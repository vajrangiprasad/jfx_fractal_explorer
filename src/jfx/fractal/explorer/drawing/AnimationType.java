package jfx.fractal.explorer.drawing;

public enum AnimationType {
	SPIRAL("Spiral"),
	SIZE("Size"),
	ROTATION("Rotation");
	
	private String type;
	
	private AnimationType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}