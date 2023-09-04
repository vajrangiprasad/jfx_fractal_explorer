package jfx.fractal.explorer.preference;

public enum PenColorType {
	TURTLE_PEN_COLOR("Turtle Pen Color"),
	RANDOM_COLR("Random Color"),
	TWO_COLOR("Two Color"),
	GRADIENT_COLOR("Gradient Color"),
	PALETTE_COLR("Palette Color"),
	RAINBOW_COLOR("Rainbow Color");
	
	private String type;
	
	private PenColorType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
