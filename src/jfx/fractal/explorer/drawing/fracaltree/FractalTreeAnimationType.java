package jfx.fractal.explorer.drawing.fracaltree;

public enum FractalTreeAnimationType {
	NONE("None"),
	ITERATION("Iteration"),
	ANGLE("Angle");
	
	private String type;
	
	private FractalTreeAnimationType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}