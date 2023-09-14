package jfx.fractal.explorer.drawing.gardi;

public enum GardiFractalOrientation {
	HORIZONTAL("Horizontal"),
	VERTICAL("Vertical");
	
	private String orientation;
	
	private GardiFractalOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	@Override
	public String toString() {
		return orientation;
	}
}
