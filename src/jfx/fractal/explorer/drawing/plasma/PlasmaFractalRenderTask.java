package jfx.fractal.explorer.drawing.plasma;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.drawing.canvas.FractalCanvas;
import jfx.fractal.explorer.util.StdRandom;

public class PlasmaFractalRenderTask extends FractalDrawingRenderTask {
	private FractalCanvas canvas;
	private double hueTopLeft;
	private double hueTopRight;
	private double hueBottomLeft;
	private double hueBottomRight;
	private double size = 450.0;
	private double minSize = 0.5;
	private double saturation = 1.0;
	private double brightness = 1.0;
	
	private double standardDeviation = 1.0;
	private PlasmaFractalPreference preference = PlasmaFractalPreference.getInstance();
	
	public PlasmaFractalRenderTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,FractalCanvas canvas) {
		super(fractalExplorer, taskType);
		this.canvas = canvas;
		
		hueTopLeft = StdRandom.uniform();
		hueTopRight = StdRandom.uniform();
		hueBottomLeft = StdRandom.uniform();
		hueBottomRight = StdRandom.uniform();
		
		size = preference.getSize();
		minSize = preference.getMinSize();
		saturation = preference.getSaturation();
		brightness = preference.getBrightness();
		standardDeviation = preference.getStandardDeviation();
	}

	@Override
	public void draw() {
		if(taskType == FractalRenderTaskType.DRAW) {
			updateMessage("Plasma Fracal Rendering ....");
			updateProgress(0, 100);
		}
		renderPlasma(size, size, size, standardDeviation, hueTopLeft, hueTopRight, hueBottomLeft, hueBottomRight);
		canvas.refreshScreen();
		updateMessage(null);
	}

	@Override
	public void animate() {
		updateMessage("Plasma Fracal Animation running ....");
		updateProgress(0, 100);
		while(!preference.isJobCanceled()) {
			draw();
			try {
				Thread.sleep((long)(1*1000));
			} catch (InterruptedException e) {
			}
		}
		updateMessage(null);
	}

	private void renderPlasma(double x, double y, double s,double stddev,double c1,double c2,double c3,double c4) {
		try {
		if(preference.isJobCanceled()) {
			return;
		}
		if( s <= minSize) {
			return;
		}
		double displacement = StdRandom.gaussian(0, stddev);
		double cM = (c1 + c2 + c3 + c4) / 4.0 + displacement;
		double cT = (c1 + c2) / 2.0;    // top
	    double cB = (c3 + c4) / 2.0;    // bottom
	    double cL = (c1 + c3) / 2.0;    // left
	    double cR = (c2 + c4) / 2.0;    // right
	    
	    if(cM < 0) {
	    	cM*=-1.0;
	    }
	    if(cM > 1.0) {
	    	cM = 1.0;
	    }
	    
	   Color c = preference.isGrayScale() ? Color.gray(cM) : Color.hsb(cM*360.0, saturation, brightness);
	   
	    fillSquare(x, y, s, c);
	    
	    renderPlasma(x - s/2, y - s/2, s/2, stddev/2, cL, cM, c3, cB);
	    renderPlasma(x + s/2, y - s/2, s/2, stddev/2, cM, cR, cB, c4);
	    renderPlasma(x - s/2, y + s/2, s/2, stddev/2, c1, cT, cL, cM);
	    renderPlasma(x + s/2, y + s/2, s/2, stddev/2, cT, c2, cM, cR);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void fillSquare(double x, double y,double s,Color c) {
		canvas.setFillColor(c);
		canvas.fillSquare(x, y, s);
	}
}
