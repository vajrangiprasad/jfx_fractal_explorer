package jfx.fractal.explorer.drawing;

import javafx.concurrent.Task;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;

public abstract class FractalDrawingRenderTask extends Task<Void>  {
	protected JFXFractalExplorer fractalExplorer;
	protected FractalRenderTaskType taskType;
    
    public FractalDrawingRenderTask(JFXFractalExplorer fractalExplorer,FractalRenderTaskType taskType) {
    	this.fractalExplorer = fractalExplorer;
    	this.taskType = taskType;
    }
    
    @Override
    protected Void call() throws Exception {
    	switch (taskType) {
			case ANIMATE: {
				animate();
				break;
			}
			case DRAW : {
				draw();
				break;
			}
    	}
    	return null;
    }
    
    public abstract void draw();
    public abstract void animate();
}
