package jfx.fractal.explorer.drawing.koch.snoflake;

public enum KochSnowFlakeType {
	TRAINGULAR_KOCH_SNOWFLAKE("Triangular Koch Snowflake"),
	TRAINGULAR_ANTI_KOCH_SNOWFLAKE("Triangular Anti Koch Snowflake"),
	TRAINGULAR_QUDRATIC_SNOWFLAKE("Trianglular Quadratic Snowlake"),
	TRIALNGULAR_ANTI_QUADRATIC_SNOWFLAKE("Trianglular Anti Quadratic Snowlake"),
	SQUARE_QUADRATIC_SNOWFLAKE("Square Quadratic Snowlake"),
	SQUARE_ANTI_QUADRATIC_SNOWFLAKE("Square Anti Quadratic Snowlake"),
	CESARO_SNOWFLAKE("Cesaro Snowflake"),
	CESARO_ANTI_SNOWFLAKE("Cesaro Anti Snowflake"),
	CESARO_TRAINGULAR_SNOWFLAKE("Cesaro Triangular Snowflake"),
	CESARO_TRAILNGULAR_ANTI_SNOWFLAKE("Cesaro Traingular Anti Snowflake"),
	HEXA_FLAKE("Hexaflake"),
	OCTA_FLAKE("Octaflake");
	
	private String type;
	
	private KochSnowFlakeType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
