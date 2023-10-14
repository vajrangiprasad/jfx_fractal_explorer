package jfx.fractal.explorer.preference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.color.palette.ColorPalette;

public class ColorPreference implements Observable{
	private Color penColor = Color.RED;
	private Color fillColor = Color.YELLOW;
	private Color alternateColor1 = Color.RED;
	private Color alternateColor2 = Color.YELLOW;
	private Color backgroundColor = Color.BLACK;
	private PenColorType penColorType = PenColorType.TURTLE_PEN_COLOR;
	private FillColorType fillColorType = FillColorType.PALETTE_COLR;
	private ObservableList<ColorPalette> colorPalettes = FXCollections.observableArrayList();
	private ColorPalette selectedColorPalette;
	private List<InvalidationListener> invalidationListeners = new ArrayList<InvalidationListener>();
	private static ColorPreference colorPreference;
	
	private ColorPreference() {
		loadColorPalettes();
	}
	
	public static ColorPreference getInstance() {
		if(colorPreference == null) {
			colorPreference = new ColorPreference();
		}
		
		return colorPreference;
	}
	
	public Color getPenColor() {
		return penColor;
	}
	public void setPenColor(Color penColor) {
		this.penColor = penColor;
		invalidate();
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		invalidate();
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		invalidate();
	}
	
	
	public ColorPalette getSelectedColorPalette() {
		return selectedColorPalette;
	}

	public void setSelectedColorPalette(ColorPalette selectedColorPalette) {
		this.selectedColorPalette = selectedColorPalette;
		invalidate();
	}

	
	public Color getAlternateColor1() {
		return alternateColor1;
	}

	public void setAlternateColor1(Color alternateColor1) {
		this.alternateColor1 = alternateColor1;
		invalidate();
	}

	public Color getAlternateColor2() {
		return alternateColor2;
	}

	public void setAlternateColor2(Color alternateColor2) {
		this.alternateColor2 = alternateColor2;
		invalidate();
	}

	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate();
	}

	public FillColorType getFillColorType() {
		return fillColorType;
	}

	public void setFillColorType(FillColorType fillColorType) {
		this.fillColorType = fillColorType;
		invalidate();
	}

	public ObservableList<ColorPalette> getColorPalettes() {
		return colorPalettes;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		invalidationListeners.add(listener);		
	}
	
	@Override
	public void removeListener(InvalidationListener listener) {
		invalidationListeners.remove(listener);
	}
	
	public static Color getGradientColor(int index,int numberOfColors,Color c1,Color c2) {
		float ratio = (float) index / (float) numberOfColors;
		double red =  (c2.getRed() * ratio + c1.getRed() * (1 - ratio));
		double green =  (c2.getGreen() * ratio + c1.getGreen() * (1 - ratio));
		double blue =  (c2.getBlue() * ratio + c1.getBlue() * (1 - ratio));
        
		if(red > 1.0) {
			red = 1.0;
		}
		
		if(green > 1.0 ) {
			green = 1.0;
		}
		
		if(blue > 1.0) {
			blue = 1.0;
		}
		
        return new Color(red, green, blue, 1.0);
	}
	
	public static Color[] createRainbowColors(int numberOfColors) {
		Color[] colors = new Color[numberOfColors];
		
		float hue = 0.0f;
		for(int i = 0; i <numberOfColors;i++) {
			colors[i] = Color.hsb(hue, 1.0f, 1.0f);
			hue += 360.0/(float)numberOfColors;
		}
		return colors;
	}
	
	public static Color getRandomColor() {
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		
		return Color.rgb(r, g, b, 1.0);
	}
	
	public static Color[] createRandomColors(int numberOfColors)
	{
	    Color[] colors = new Color[numberOfColors];
	    double r = Math.random();
	    int red = 0, green = 0, blue = 0;
	    for (int i = 0; i < numberOfColors; i++)
        {
            red = 13*(numberOfColors-i) % numberOfColors;
            green = 7*(numberOfColors-i) % numberOfColors;
            blue = 11*(numberOfColors-i) % numberOfColors;
            
            if (r < 1.0/6.0) {
            	colors[i] = Color.rgb(red, green, blue);
            }else if (r < 2.0/6.0) {
            	colors[i] = Color.rgb(red, blue, green);
            }else if (r < 3.0/6.0) {
            	colors[i] = Color.rgb(green, red, blue);
            }else if (r < 4.0/6.0) {
            	colors[i] = Color.rgb(green, blue, red);
            }else if (r < 5.0/6.0) {
            	colors[i] = Color.rgb(blue, red, green);
            }else if (r < 6.0/6.0) {
            	colors[i] = Color.rgb(blue, green, red);
            }
        }
	  
	    return colors;
	}
	
	private void invalidate() {
		invalidationListeners.forEach(listener -> {
			listener.invalidated(this);
		});
	}
	
	private void loadColorPalettes() {
		List<ColorPalette> tempList ;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			byte[] jsonData = Files.readAllBytes(Paths.get("ColorPalettes.json"));
			tempList = objectMapper.readValue(jsonData, new TypeReference<List<ColorPalette>>(){});
		}catch(IOException ioe) {
			ioe.printStackTrace();
			tempList = new ArrayList<ColorPalette>();
			tempList.add(new ColorPalette("Spectrum"));
		}
		
		colorPalettes.addAll(tempList);
		setSelectedColorPalette(colorPalettes.get(0));
	}
}
