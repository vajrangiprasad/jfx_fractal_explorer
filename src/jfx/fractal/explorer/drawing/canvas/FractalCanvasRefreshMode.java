package jfx.fractal.explorer.drawing.canvas;

public enum FractalCanvasRefreshMode {
	ON_CHANGE("On Change"),
	ON_DEMAND("On Demand");
	
	private String refreshMode;
	
	private FractalCanvasRefreshMode(String refreshMode) {
		this.refreshMode = refreshMode;
	}
	
	@Override
	public String toString() {
		return refreshMode;
	}
}
