package jfx.fractal.explorer.preference;

public enum FillColorType {
	NO_FILL("No Fill"),
	TURTLE_FILL_COLOR("Turtle Fill Color"),
	RAINBOW_COLOR("Rainbow Color"),
	RANDOM_COLR("Random Color"),
	TWO_COLOR("Two Color"),
	GRADIENT_FILL("Gradient Color"),
	PALETTE_COLR("Palette Color");
	
	
	private String type;
	
	private FillColorType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
