package jfx.fractal.explorer.drawing.squaretree;

public enum SquareTreeAnimationType {
	NONE("None"),
	ITERATION("Iteration");
	
	private String type;
	
	private SquareTreeAnimationType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}