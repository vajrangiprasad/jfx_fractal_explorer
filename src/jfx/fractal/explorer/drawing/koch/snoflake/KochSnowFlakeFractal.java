package jfx.fractal.explorer.drawing.koch.snoflake;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.turtle.Turtle;

public class KochSnowFlakeFractal implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer ;
	private KochSnowFlakePreferencePane kochSnowFlakePreferencePane ;
	private KochSnowFlakePreference kochSnowFlakePreference = KochSnowFlakePreference.getInstance();
	private Turtle turtle ;
	
	public KochSnowFlakeFractal(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		kochSnowFlakePreferencePane = new KochSnowFlakePreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		kochSnowFlakePreference.addListener(this);
	}
	@Override
	public void draw() {
		clearDrawing();
		jfxFractalExplorer.disableControls();
		FractalDrawingRenderTask task;
		switch(kochSnowFlakePreference.getType()) {
		case SQUARE_QUADRATIC_SNOWFLAKE:
		case SQUARE_ANTI_QUADRATIC_SNOWFLAKE:
			task = new SquareQuadraticSnowFlakeTask(jfxFractalExplorer, 
					FractalRenderTaskType.DRAW, 
					turtle);
			break;
		case TRAINGULAR_KOCH_SNOWFLAKE:
		case TRAINGULAR_ANTI_KOCH_SNOWFLAKE:
			task = new TraingularKochSnowFlakeTask(jfxFractalExplorer, 
					FractalRenderTaskType.DRAW, 
					turtle);
			break;
		case TRAINGULAR_QUDRATIC_SNOWFLAKE:
		case TRIALNGULAR_ANTI_QUADRATIC_SNOWFLAKE:
			task = new TraingularQuadraticSnowFlakeTask(jfxFractalExplorer, 
					FractalRenderTaskType.DRAW, 
					turtle);
			break;
		case CESARO_SNOWFLAKE:
		case CESARO_ANTI_SNOWFLAKE:
			task = new CesaroSnowFlakeTask(jfxFractalExplorer, 
					FractalRenderTaskType.DRAW, 
					turtle);
			break;
		case CESARO_TRAILNGULAR_ANTI_SNOWFLAKE:
		case CESARO_TRAINGULAR_SNOWFLAKE:
			task = new CesaroTraingularSnowFlakeTask(jfxFractalExplorer, 
					FractalRenderTaskType.DRAW, 
					turtle);
			break;
		case HEXA_FLAKE:
			task = new HexaFlakeTask(jfxFractalExplorer, 
					FractalRenderTaskType.DRAW, 
					turtle);
			break;
		case OCTA_FLAKE:
			task = new OctaFlakeTask(jfxFractalExplorer, 
					FractalRenderTaskType.DRAW, 
					turtle);
			break;
		default :
			task = new SquareQuadraticSnowFlakeTask(jfxFractalExplorer, 
					FractalRenderTaskType.DRAW, 
					turtle);
		}
		
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		jfxFractalExplorer.showErrorMessage("Animation is not supported in Koch Snow Flake Fractal");
	}

	@Override
	public void stopRendering() {
		kochSnowFlakePreference.setJobCanceled(true);
	}

	@Override
	public Node getControlNode() {
		return kochSnowFlakePreferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer);
		turtle.setWorldCordinates(-1000.0, 1000.0, -1000.0, 1000.0);
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		clearDrawing();
		kochSnowFlakePreference.removeListener(this);
	}

	@Override
	public void disableControls() {
		kochSnowFlakePreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		kochSnowFlakePreferencePane.enableControls();
	}

	@Override
	public void invalidated(Observable observable) {
		clearDrawing();
		draw();
	}

}
