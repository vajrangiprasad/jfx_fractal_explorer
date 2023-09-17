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
    	fractalExplorer.getLblStatusMessage().textProperty().unbind();
    	fractalExplorer.getLblStatusMessage().textProperty().bind(messageProperty());
    	fractalExplorer.getProgressBar().progressProperty().unbind();
    	fractalExplorer.getProgressBar().progressProperty().bind(progressProperty());
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
    
    @Override
    protected void cancelled() {
    	super.cancelled();
    	fractalExplorer.enableControls();
    }
    
    @Override
    protected void failed() {
    	super.failed();
    	fractalExplorer.enableControls();
    }
    
    @Override
    protected void succeeded() {
    	super.succeeded();
    	fractalExplorer.enableControls();
    }
    public abstract void draw();
    public abstract void animate();
}
