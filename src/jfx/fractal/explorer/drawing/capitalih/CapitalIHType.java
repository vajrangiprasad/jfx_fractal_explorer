package jfx.fractal.explorer.drawing.capitalih;

public enum CapitalIHType {
	I("I Type"),
	H("H Type"),
	IH("IH Type");
	
private String type;
	
	private CapitalIHType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
