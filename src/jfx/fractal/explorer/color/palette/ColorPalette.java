package jfx.fractal.explorer.color.palette;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.paint.Color;

public class ColorPalette implements Cloneable{
	private String name;
	private ColorPaletteType colorPaletteType ;
	private ArrayList<DivisionPoint> divisionPoints = new ArrayList<DivisionPoint>();
	private int numberOfColors;
	private int offset;
	
	public ColorPalette() {
		this("Spectrum");
	}
	
	public ColorPalette(String name) {
		this(name,ColorPaletteType.HSB);
	}
	
	public ColorPalette(String name,ColorPaletteType colorPaletteType) {
		this(name,colorPaletteType,100,0);
	}
	
	public ColorPalette(String name,ColorPaletteType colorPaletteType,int numberOfColors,int offset) {
		this.name = name;
		this.colorPaletteType = colorPaletteType;
		this.numberOfColors = numberOfColors;
		this.offset = offset;
		
		switch(colorPaletteType) {
		case HSB:
			divisionPoints.add(new DivisionPoint(0.0, 0, 1, 1));
			divisionPoints.add(new DivisionPoint(1.0, 1, 1, 1));
			break;
		case RGB:
			divisionPoints.add(new DivisionPoint(0.0, 1, 1, 1));
			divisionPoints.add(new DivisionPoint(1.0, 0, 0, 0));
			break;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ColorPaletteType getColorPaletteType() {
		return colorPaletteType;
	}

	public void setColorPaletteType(ColorPaletteType colorPaletteType) {
		this.colorPaletteType = colorPaletteType;
	}

	public int getNumberOfColors() {
		return numberOfColors;
	}

	public void setNumberOfColors(int numberOfColors) {
		this.numberOfColors = numberOfColors;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public ArrayList<DivisionPoint> getDivisionPoints() {
		return divisionPoints;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@JsonIgnore
	public int getDivisionPointCount() {
		return divisionPoints.size();
	}
	
	@JsonIgnore
	public DivisionPoint getDivisionPoint(int index) {
		if(index < 0 || index > divisionPoints.size() ) {
			throw new IndexOutOfBoundsException("Division point index out of range: " +index);
		}
		
		return divisionPoints.get(index);
	}
	
	public int split(double divisionPoint) {
		int index = 0;

		if (divisionPoint <= 0 || divisionPoint >= 1 || Double.isNaN(divisionPoint))
			throw new IllegalArgumentException("Division point out of range: " + divisionPoint);

		while (divisionPoint > divisionPoints.get(index).getPoint())
			index++;
		if (Math.abs(divisionPoint - divisionPoints.get(index).getPoint()) < 1e-15) {
			index = -1;
			return -1;
		}
		float ratio = (float) ((divisionPoint - divisionPoints.get(index - 1).getPoint())
				/ (divisionPoints.get(index).getPoint() - divisionPoints.get(index - 1).getPoint()));
		float[] c1 = divisionPoints.get(index - 1).getPointColors();
		float[] c2 = divisionPoints.get(index).getPointColors();
		float a = c1[0] + ratio * (c2[0] - c1[0]);
		float b = c1[1] + ratio * (c2[1] - c1[1]);
		float c = c1[2] + ratio * (c2[2] - c1[2]);
		DivisionPoint dv = new DivisionPoint(divisionPoint, a, b, c);
		divisionPoints.add(index, dv);

		return index;
	}
	
	public void join(int index) {
		if (index <= 0 || index >= divisionPoints.size() - 1)
			throw new IllegalArgumentException("Division point index out of range: " + index);
		divisionPoints.remove(index);
	}
	
	@JsonIgnore
	public Color getColor(double position) {
		if (position < 0 || position > 1)
			throw new IllegalArgumentException("Position out of range " + position);
		int pt = 1;
		while (position > divisionPoints.get(pt).getPoint())
			pt++;
		float ratio = (float)( (position - divisionPoints.get(pt-1).getPoint()) 
				/ (divisionPoints.get(pt).getPoint() - divisionPoints.get(pt-1).getPoint()) );
		float[] c1 = divisionPoints.get(pt-1).getPointColors() ;
		float[] c2 = divisionPoints.get(pt).getPointColors();
		float a = clamp1(c1[0] + ratio*(c2[0] - c1[0]));
		float b = clamp2(c1[1] + ratio*(c2[1] - c1[1]));
		float c = clamp2(c1[2] + ratio*(c2[2] - c1[2]));
		Color color = null;
		switch(colorPaletteType) {
		case HSB:
			color = Color.hsb(360*a, b, c);
			break;
		case RGB:
			color = new Color(a, b, c,1.0);
			break;
		}
					
		return color;
	}
	
	@JsonIgnore
	public Color getDivisionPointColor(int index) {
		float[] components = divisionPoints.get(index).getPointColors();
		float a = clamp1(components[0]);
		float b = clamp2(components[1]);
		float c = clamp2(components[2]);
		if (colorPaletteType == ColorPaletteType.RGB)
			return new Color(a,b,c,1.0);
		else
			return Color.hsb(360*a, b, c);
	}
	
	public void setDivisionPoint(int index, double position) {
		if (index <= 0 || index >= divisionPoints.size() - 1)
			throw new IllegalArgumentException("Division Point index out of range " + index);
		if (position <= divisionPoints.get(index-1).getPoint() || position >= divisionPoints.get(index+1).getPoint())
			throw new IllegalArgumentException("Division point position out of range " + position);
		if (position != divisionPoints.get(index).getPoint()) {
			divisionPoints.get(index).setPoint(position);
		}
	}
	
	public void setDivisionPointProperties(int index, double position,float c1,float c2,float c3) {
		if(position > 0.0 && position < 1.0) {
			if (index <= 0 || index >= divisionPoints.size() - 1)
				throw new IllegalArgumentException("Division point index out of range "+index);
			if (position <= divisionPoints.get(index-1).getPoint() || position >= divisionPoints.get(index+1).getPoint())
				throw new IllegalArgumentException("Division point position out of range " + position);
			if (position != divisionPoints.get(index).getPoint()) {
				divisionPoints.get(index).setPoint(position);
			}
		}
		
		divisionPoints.get(index).setPointColors(c1, c2, c3);
	}
	
	public void setDivisionPointColorComponents(int index,float c1,float c2,float c3) {
		if (index <= 0 || index >= divisionPoints.size() - 1) {
			throw new IllegalArgumentException("Division point index out of range "+index);
		}
		
		divisionPoints.get(index).setPointColors(c1, c2, c3);
	}
	
	public Color[] makeRGBs() {
		return makeRGBs(numberOfColors,offset);
	}
	
	public Color[] makeRGBs(int nColors,int offset) {
		if (nColors == 0) {
			nColors = 1;
		}
		Color[] colors = new Color[nColors];
		colors[offset % nColors] = getDivisionPointColor(0);
		int ct = 1;
		double dx = 1.0 / (nColors-1);
		int pt = 1;
		while (ct < nColors-1) {
			double position = dx*ct;
			while (position > divisionPoints.get(pt).getPoint())
				pt++;
			float ratio = (float)( (position - divisionPoints.get(pt-1).getPoint()) 
					/ (divisionPoints.get(pt).getPoint() - divisionPoints.get(pt-1).getPoint()) );
			float[] c1 = divisionPoints.get(pt-1).getPointColors();
			float[] c2 = divisionPoints.get(pt).getPointColors();
			float a = clamp1(c1[0] + ratio*(c2[0] - c1[0]));
			float b = clamp2(c1[1] + ratio*(c2[1] - c1[1]));
			float c = clamp2(c1[2] + ratio*(c2[2] - c1[2]));
			Color color;
			if (colorPaletteType == ColorPaletteType.HSB)
				color = Color.hsb(360*a, b, c);
			else
				color = new Color(a, b, c,1.0);
			colors[(ct + offset) % nColors] = color;
			ct++;
		}
		colors[(offset + nColors-1) % nColors] = getDivisionPointColor(divisionPoints.size()-1);
		return colors;
	}
	
	private float clamp1(float x) {
		if (colorPaletteType == ColorPaletteType.HSB)
			return x - (float)Math.floor(x);
		else 
			return clamp2(x);
	}
	
	private float clamp2(float x) {
		x = 2*(x/2 - (float)Math.floor(x/2));
		if (x > 1)
			x = 2 - x;
		return x;
	}
}
