package jfx.fractal.explorer.drawing.gardi;

import javafx.concurrent.Task;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.turtle.Turtle;

public class GardiFractalRenderTask extends Task<Void> {
	private JFXFractalExplorer fractalExplorer;
    private FractalRenderTaskType taskType;
    private Turtle turtle;
    private GardiFractalPreference gardiFractalPreference = GardiFractalPreference.getInstance();
    
	public GardiFractalRenderTask(JFXFractalExplorer fractalExplorer,
			FractalRenderTaskType taskType,
			Turtle turtle) {
		this.fractalExplorer = fractalExplorer;
		this.taskType = taskType;
		this.turtle = turtle;
	}
	
	@Override
	protected Void call() throws Exception {
		int orientation = 0;
		switch(gardiFractalPreference.getOrientation()) {
		case HORIZONTAL:
			orientation = 0;
			break;
		case VERTICAL :
			orientation = 1;
			break;
		}
		switch (taskType) {
			case DRAW: {
				drawGardi(0,
						0,
						orientation,
						gardiFractalPreference.getIterations(),
						gardiFractalPreference.getRadius());
				break;
			}
			case ANIMATE : {
				fractalExplorer.showErrorMessage("Animation is not supported in GArdi Fractal");
				break;
			}
		}
		
		return null;
	}
	
	private void drawGardi(double x,double y,int orientation,int iteration,double radius) {
		if(iteration == 0) {
			return;
		}
		drawTwoCircles(x,y,radius,orientation);
		drawGardi(x,
				y, 
				1-orientation,
				iteration-1,
				Math.abs((4-Math.pow(7, 0.5))/3*radius)
				);
	}
	
	private void drawTwoCircles(double x, double y,double r,int orientation) {
		turtle.setPenSize(r/50);
		//turtle.setPenColor(colors[circleIndex%iteration]);
		//circleIndex++;
		if(orientation == 0) {
			drawCircle(x-r/2, y, r);
			drawCircle(x+r/2, y, r);
		}else {
			drawCircle(x, y-r/2, r);
			drawCircle(x, y+r/2, r);
		}
	}
	
	private void drawCircle(double x, double y,double r) {
		turtle.penUp();
		turtle.moveTo(x, y);
		turtle.setDirection(0);
		turtle.penDown();
		turtle.circle(r);
	}

}
