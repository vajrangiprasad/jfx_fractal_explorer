package jfx.fractal.explorer.turtle;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.preference.PreferenceManager;
import jfx.fractal.explorer.preference.TurtlePreference;
import jfx.fractal.explorer.util.FractalUtility;

public class TurtleCommand implements Runnable {
	private TurtleTrail trail;
	private TurtlePreference turtlePreference = PreferenceManager.getInstance().getTurtlePreference();
	
	public TurtleCommand(TurtleTrail trail) {
		this.trail = trail;
	}
	
	public TurtleTrail getTrail() {
		return trail;
	}

	private void drawTurtle() {
		if(!trail.isTurtleVisible()) {
			return;
		}
		
		GraphicsContext turtleGC = trail.getTurtleGC();
		turtleGC.save();
		turtleGC.clearRect(0, 0, FractalConstants.FRACTAL_DISPLAY_SIZE, FractalConstants.FRACTAL_DISPLAY_SIZE);
		TurtleShape shape = trail.getShape();
		BoundingBox bbox = FractalUtility.getBoundinBox(shape);
		double sw = bbox.getWidth();
		double sh = bbox.getHeight();
		
		double x1, y1, cx, cy;
		Point2D position = trail.getPosition();
		Point2D center = trail.getCenter();
		x1 = position.getX();
		y1 = position.getY();
		cx = center.getX();
		cy = center.getY();
		double x = ((x1 - cx) + FractalConstants.FRACTAL_DISPLAY_SIZE / 2);
		double y = ((y1 - cy) * (-1) + FractalConstants.FRACTAL_DISPLAY_SIZE / 2);
		
		turtleGC.translate(x, y);
		turtleGC.rotate(trail.getDirection());
		turtleGC.translate(-sw / 2, -sh / 2);
		turtleGC.setStroke(trail.getPenColor());
		turtleGC.setFill(trail.getFillColor());
		turtleGC.setLineWidth(3.0);
		turtleGC.strokePolygon(shape.getX(), shape.getY(), shape.getPoints());
		turtleGC.fillPolygon(shape.getX(), shape.getY(), shape.getPoints());
		turtleGC.restore();
	}
	
	private void drawTrail() {
		if(!trail.isPenDown() || trail.getStrokeType() == TurtleStrokeType.NONE) {
			return;
		}
		
		double x1, y1, x2, y2, cx, cy, r;
		double width = trail.getWidth();
		double height = trail.getHeight();

		cx = trail.getCenter().getX();
		cy = trail.getCenter().getY();
		x1 = (trail.getOldPosition().getX() - cx) + width / 2;
		y1 = (trail.getOldPosition().getY() - cy) * (-1) + height / 2;
		x2 = (trail.getPosition().getX() - cx) + width / 2;
		y2 = (trail.getPosition().getY() - cy) * (-1) + height / 2;
		r = trail.getRadius();
		
		GraphicsContext gc = trail.getDrawingGC();
		try {
			gc.save();
			gc.setStroke(trail.getPenColor());
			gc.setFill(trail.getFillColor());
			gc.setLineWidth(turtlePreference.getPenSize());
			gc.setGlobalAlpha(turtlePreference.getAlpha());
			gc.setGlobalBlendMode(turtlePreference.getBlendMode());
			gc.setLineCap(turtlePreference.getStrokeLineCap());
			gc.setLineJoin(turtlePreference.getStrokeLineJoin());
			
			switch (trail.getStrokeType()) {
			case LINE:
				gc.strokeLine(x1, y1, x2, y2);
				break;
			case DOT:
				gc.fillOval(x2 - r, y2 - r, 2 * r, 2 * r);
				break;
			case CIRCLE:
				gc.strokeOval(x2 - r, y2 - r, 2 * r, 2 * r);
				break;
			case FILLED_CIRCLE:
				gc.strokeOval(x2 - r, y2 - r, 2 * r, 2 * r);
				gc.fillOval(x2 - r, y2 - r, 2 * r, 2 * r);
				break;
			case CLEAR:
				gc.clearRect(0, 0, width, height);
				break;
			default:
				break;
			}

			if (trail.isEndFilling()) {
				double[] xArray = FractalUtility.getDoubleArrray(trail.getxList());
				double[] yArray = FractalUtility.getDoubleArrray(trail.getyList());
				gc.strokePolygon(xArray, yArray, trail.getxList().size());
				gc.fillPolygon(xArray, yArray, trail.getxList().size());
			}
		} finally {
			gc.restore();
		}
	}

	@Override
	public void run() {
		drawTrail();
		drawTurtle();
	}
}
