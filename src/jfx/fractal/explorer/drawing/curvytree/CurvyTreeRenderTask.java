package jfx.fractal.explorer.drawing.curvytree;

import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.turtle.Turtle;

public class CurvyTreeRenderTask extends FractalDrawingRenderTask {
	private Turtle turtle;
	private int totalLines = 0;
	private int currentLine = 0;
	private CurvyTreePreference curvyTreePreference = CurvyTreePreference.getInstance();
	
	public CurvyTreeRenderTask(JFXFractalExplorer fractalExplorer, 
			FractalRenderTaskType taskType,
			Turtle turtle) {
		super(fractalExplorer, taskType);
		this.turtle = turtle;
	}

	@Override
	public void draw() {
		curvyTreePreference.setJobCanceled(false);
		countLines(curvyTreePreference.getLength());
		curvyTree(400, -500, curvyTreePreference.getLength(), 90);
		turtle.refreshScreen();
	}

	@Override
	public void animate() {
		updateMessage("Rendering Curvy Tree Animation running ..." );
		updateProgress(0.0, 100.0);
		while(!curvyTreePreference.isJobCanceled()) {
			switch (curvyTreePreference.getAnimationType()) {
			case LENGTH:
				animation_length_up();
				animation_length_down();
				break;
			default:
				fractalExplorer.showErrorMessage("Curvy Tree Animation type " +curvyTreePreference.getAnimationType() + " Not Supported");
				break;
			}
		}
		updateMessage(null );
	}

	private void animation_length_up() {
		for(int l = 0; l< CurvyTreePreference.MAX_LENGTH;l+=10) {
			turtle.clear();
			curvyTree(400, -500, l, 90);
			turtle.refreshScreen();
			if(curvyTreePreference.getAnimationDelay() > 0.0) {
				try {
					Thread.sleep((long)(curvyTreePreference.getAnimationDelay()*1000));
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	private void animation_length_down() {
		for(int l =  CurvyTreePreference.MAX_LENGTH; l >= 1;l-=10) {
			turtle.clear();
			curvyTree(400, -500, l, 90);
			turtle.refreshScreen();
			if(curvyTreePreference.getAnimationDelay() > 0.0) {
				try {
					Thread.sleep((long)(curvyTreePreference.getAnimationDelay()*1000));
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	private void countLines(double length) {
		if(length <3) {
			return;
		}
		
		totalLines++;
		countLines(length*0.9);
		countLines(length*0.45);
	}
	
	private void curvyTree(double x, double y, double length,double tilt) {
		if(length < 3 || curvyTreePreference.isJobCanceled()) {
			return;
		}
		
		if(taskType == FractalRenderTaskType.DRAW) {
			currentLine++;
			updateMessage("Rendering Curvy Tree line " +currentLine + " of " + totalLines );
			updateProgress((double)currentLine/(double)totalLines*100.0, 100.0);
		}
		turtle.penUp();
		turtle.moveTo(x, y);
		turtle.setPenSize(length/30);
		turtle.penDown();
		turtle.setDirection(-tilt);
		if(length < 30) {
			turtle.setPenColor(curvyTreePreference.getLeafColor());
		}else {
			turtle.setPenColor(curvyTreePreference.getStemColor());
		}
		turtle.forward(length);
		
		double x1 = turtle.getPosition().getX();
		double y1 = turtle.getPosition().getY();
		
		curvyTree(x1, y1, length*0.9, (tilt+curvyTreePreference.getAngle1()));
		
		curvyTree(x1, y1, length*0.45, tilt-curvyTreePreference.getAngle2());
	}
}
