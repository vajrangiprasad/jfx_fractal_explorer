package jfx.fractal.explorer.drawing.mandelbrotset;

public enum MandelbrotMouseActionType {
	ZOOM_IN("Zoom In"),
	SHOW_ORBIT("Show Orbit"),
	RECENTER("Recener"),
	SHOW_JULIA_SET("Show Julia Set");
	
	private String type;
	
	private MandelbrotMouseActionType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
