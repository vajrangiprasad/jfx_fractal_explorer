package jfx.fractal.explorer.drawing.mandelbrotset;

public enum MandelbrotType {
	MANDELBROT_SET("Mandelbrot Set"),
	BURNING_SHIP("Burning Ship"),
	ALFARO("Alfaro"),
	FALTWORM("Flatworm"),
	TRICORN("Tricorn");
	
	private String type;
	
	private MandelbrotType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}