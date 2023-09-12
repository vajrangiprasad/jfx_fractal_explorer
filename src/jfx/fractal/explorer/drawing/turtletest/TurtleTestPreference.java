package jfx.fractal.explorer.drawing.turtletest;

public class TurtleTestPreference {
	private double stepSize = 100.0;
	private double angle = 90.0;
	private static TurtleTestPreference turtleTestPreference;
	
	private TurtleTestPreference() {
		
	}
	
	public static TurtleTestPreference getInstance() {
		if(turtleTestPreference == null) {
			turtleTestPreference = new TurtleTestPreference();
		}
		
		return turtleTestPreference;
	}
	
	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	
}
