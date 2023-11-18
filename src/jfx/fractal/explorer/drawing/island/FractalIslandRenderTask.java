package jfx.fractal.explorer.drawing.island;

import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.turtle.Turtle;
import jfx.fractal.explorer.util.FractalUtility;
import jfx.fractal.explorer.util.StdRandom;

public class FractalIslandRenderTask extends FractalDrawingRenderTask  {
	private Turtle turtle;
	private FractalIslandPreference preference = FractalIslandPreference.getInstance();
	private double size;
	private static double MAX_SIZE = FractalConstants.FRACTAL_DISPLAY_SIZE/2-50;
	
	public FractalIslandRenderTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,Turtle turtle) {
		super(fractalExplorer, taskType);
		this.turtle = turtle;
	}

	@Override
	public void draw() {
		size = MAX_SIZE * preference.getSizePercent()/100.0;
		updateProgress(0, 100.0);
		drawBackground();
		turtle.setFillColor(preference.getFillColor());
		turtle.beginFilling();
		drawShoreLine(-size,0.0,size,0.0) ;
		drawShoreLine(size,0.0,-size,0.0) ;
		turtle.endFilling();
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
	}
	
	private void drawShoreLine(double x1,double y1,double x2,double y2) {
		double distance = FractalUtility.getDistance(x1, y1, x2, y2);
		
		if(distance <= 1) {
			drawLine(x1, y1, x2, y2);
			return;
		}
		
		double rs = preference.getRatio() + StdRandom.uniform(-0.1, 0.1);
		rs = Math.max(0.5, rs);
		double midX = (x1+x2)/2.0;
		double midY = (y1+y2)/2.0;
		double rx = distance/2 + (2*rs-1)/2*distance;
		double ry = Math.sqrt(Math.pow(distance*rs,2)-Math.pow(distance/2, 2));
		double theta = Math.atan2(y2-y1,x2-x1);
		double alpha = StdRandom.uniform(Math.PI*0.3,Math.PI*0.7); 
		double x3 = rx*Math.cos(alpha)*Math.cos(theta) - ry*Math.sin(alpha)*Math.sin(theta) + midX ;
		double y3 = rx*Math.cos(alpha)*Math.sin(theta) + ry*Math.sin(alpha)*Math.cos(theta) + midY;
		drawShoreLine(x1, y1, x3, y3);
		drawShoreLine(x3, y3, x2, y2);
	}
	
	private void drawLine(double x1,double y1,double x2,double y2) {
		turtle.setPenColor(preference.getEdgeColor());
		turtle.setPenSize(preference.getEdgeWidth());
		turtle.penUp();
		turtle.moveTo(x1, y1);
		turtle.penDown();
		turtle.moveTo(x2, y2);
	}
	
	private void drawBackground() {
		turtle.penUp();
		turtle.moveTo(-FractalConstants.FRACTAL_DISPLAY_SIZE/2, FractalConstants.FRACTAL_DISPLAY_SIZE/2);
		turtle.setFillColor(preference.getBackgroundColor());
		turtle.setPenColor(preference.getBackgroundColor());
		turtle.penDown();
		turtle.beginFilling();
		
		for(int i = 0; i < 4;i++) {
			turtle.forward(FractalConstants.FRACTAL_DISPLAY_SIZE);
			turtle.right(90);
		}
		
		turtle.endFilling();
		turtle.home();
	}

}
