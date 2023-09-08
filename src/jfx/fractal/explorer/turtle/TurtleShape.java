package jfx.fractal.explorer.turtle;

import java.util.HashMap;
import java.util.Map;

public class TurtleShape {
	private double[] x;
	private double[] y;
	
	private static Map<String, TurtleShape> turtleShapes = new HashMap<String, TurtleShape>();
	
	static {
		addTurtleShape();
		addArrowShape();
	}
	
	private TurtleShape(double[] x,double[] y) {
		this.x = x;
		this.y = y;
	}
	
	private static void addTurtleShape() {
		double[] xs = new double[] { 16.5,16.25,15.75,15.25,13.25,11,8.25,6,5.75,4.75,4.25,3.5,2.25,2,2,2.5,
				3.25,2.75,2.5,0.5,2.25,2.75,3.75,2.75,2.75,2.5,3,4.5,5,5.5,5.75,6.5,8.75,11,13.25,15.25,15.5,
				16,16.5,17.75,19.25,19.5,19.25,19,18,19.25,20.25,21.5,22.75,23.5,24.25,24.5,24.25,23.75,23,
				21.75,20.5,19.25,18,18.5,19.25,19.5,19,17.5};
		double[] ys = new double[] {4.5,4.75,5.25,6.25,5.75,5.5,5.75,6.75,6.25,5.25,5,5.25,6.75,7.5,8,8.5,
				9.25,10.5,11.75,12.5,13.25,14.75,16.25,17,17.25,17.75,18.5,19.75,20,20,19.5,18.5,19.25,19.5,
				19.25,18.75,19.75,20.25,20.5,20.25,19,18.25,17.75,17.25,16.5,14.75,15,15.25,15,14.5,13.5,
				12.5,11.5,10.5,10,9.75,10,10.25,8.5,8,7,6.75,6,4.75 };
		turtleShapes.put("Turtle",new TurtleShape(xs, ys));
	}
	
	private static void addArrowShape() {
		double[] xs = new double[] { 0, 25, 0, 5 };
		double[] ys = new double[] { 0, 12.5, 25, 12.5 };
		turtleShapes.put("Arrow", new TurtleShape(xs, ys));
	}
	
	public int getPoints() {
		return x.length;
	}
	
	
	
	public double[] getX() {
		return x;
	}

	public double[] getY() {
		return y;
	}

	public static TurtleShape getShape(String name) {
		TurtleShape shape = turtleShapes.get(name);
		if(shape == null) {
			shape = turtleShapes.get("Turtle");
		}
		
		return shape;
	}
}
