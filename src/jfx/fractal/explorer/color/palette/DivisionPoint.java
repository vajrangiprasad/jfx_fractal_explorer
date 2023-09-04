package jfx.fractal.explorer.color.palette;

public class DivisionPoint implements Cloneable{
	private double point;
	private float[] pointColors = new float[3];
	
	public DivisionPoint() {
		
	}
	
	public DivisionPoint(double point,float c1,float c2,float c3) {
		if(point < 0.0 || point > 1.0) {
			throw new IllegalArgumentException("Point " + point + " not in range 0.0 to 1.0");
		}
		this.point = point;
		setPointColors(c1,c2,c3);
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public float[] getPointColors() {
		return pointColors;
	}

	public void setPointColors(float[] pointColors) {
		this.pointColors = pointColors;
	}
	
	public void setPointColors(float c1,float c2,float c3) {
		pointColors[0] = c1;
		pointColors[1] = c2;
		pointColors[2] = c3;
	}
	
	@Override
	protected DivisionPoint clone()  {
		DivisionPoint point = new DivisionPoint(this.point, pointColors[0], pointColors[1], pointColors[2]) ;
		
		return point;
	}
}
