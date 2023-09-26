package jfx.fractal.explorer.drawing.curvytree;

public enum CurvyTreeAnimationType {
	LENGTH("Length"),
	ANGLE1("Angle 1"),
	ANGLE2("Angle 2");
	
	private String type;
	
	private CurvyTreeAnimationType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
