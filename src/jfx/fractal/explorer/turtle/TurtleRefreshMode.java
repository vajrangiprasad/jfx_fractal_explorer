package jfx.fractal.explorer.turtle;

public enum TurtleRefreshMode {
	ON_CHANGE("On Change"),
	ON_DEMAND("On Demand");
	
	private String refreshMode;
	
	private TurtleRefreshMode(String refreshMode) {
		this.refreshMode = refreshMode;
	}
	
	@Override
	public String toString() {
		return refreshMode;
	}
}
