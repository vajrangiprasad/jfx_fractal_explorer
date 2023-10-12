package jfx.fractal.explorer.drawing.mandelbrotset;

import java.util.ArrayList;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.util.ComplexNumber;

public class MandelbrotDrawing implements IFractalDrawing{
	private JFXFractalExplorer jfxFractalExplorer;
	private MandelbrotPreference preference = MandelbrotPreference.getInstance();
	private MandelbrotPreferencePane preferencePane;
	private MandelbrotCanvas mandelbrotCanvas;
	private Canvas mouseCanvas;
	private MouseHandler mouseHandler;
	private GraphicsContext mouseGC;
	private int pointsOnOrbit = 1000;
	private Stack<MandelbrotCordinates> zoomUndoBuffer = new Stack<>();
	
	public MandelbrotDrawing(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		preferencePane = new MandelbrotPreferencePane(jfxFractalExplorer);
		mouseHandler = new MouseHandler();
		setupDrawingCanvas();
		setupMouseCanvas();
	}

	@Override
	public void draw() {
		mandelbrotCanvas.draw();
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRendering() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node getControlNode() {
		return preferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupDrawingCanvas() {
		mandelbrotCanvas = new MandelbrotCanvas(jfxFractalExplorer);
		jfxFractalExplorer.getFractalScreen().getChildren().clear();
		jfxFractalExplorer.getFractalScreen().getChildren().add(mandelbrotCanvas);
	}

	@Override
	public void clearDrawing() {
		mandelbrotCanvas.clearDisplay();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableControls() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableControls() {
		// TODO Auto-generated method stub
		
	}
	
	public void updatePosition(double x, double y) {
		Point2D p = getPosition(x, y);
		
		jfxFractalExplorer.updateStatusMessage("Mandelbrot Set Position (" + p.getX()+","+p.getY()+")");
	}
	
	public Point2D getPosition(double x, double y,MandelbrotMouseActionType type) {
		Point2D topLeft = mandelbrotCanvas.getTopLeft();
		Point2D bottomRight = mandelbrotCanvas.getBottomRight();
		
		double dx  = Math.abs((bottomRight.getX()-topLeft.getX())/(mandelbrotCanvas.getWidth()-1));
		double dy = Math.abs((topLeft.getY()-bottomRight.getY())/(mandelbrotCanvas.getHeight()-1));
		double newX = 0.0;
		double newY = 0.0;
		if(type == MandelbrotMouseActionType.ZOOM_IN) {
			 newX = topLeft.getX() + (dx*x);
			 newY = topLeft.getY() - (dy*y);
		}
		
		return new Point2D(newX, newY);
	}
	
	public Point2D getPosition(double x, double y) {
		Point2D topLeft = mandelbrotCanvas.getTopLeft();
		Point2D bottomRight = mandelbrotCanvas.getBottomRight();
		
		double dx  = Math.abs((bottomRight.getX()-topLeft.getX())/(mandelbrotCanvas.getWidth()-1));
		double dy = Math.abs((topLeft.getY()-bottomRight.getY())/(mandelbrotCanvas.getHeight()-1));
		double newX = topLeft.getX() + (dx*x);
		double newY = topLeft.getY() - (dy*y);
		
		return new Point2D(newX, newY);
	}
	
	private void setupMouseCanvas() {
		mouseCanvas = new Canvas(FractalConstants.FRACTAL_DISPLAY_SIZE,FractalConstants.FRACTAL_DISPLAY_SIZE);
		mouseCanvas.setStyle("-fx-background-color:transparent;");
		mouseGC = mouseCanvas.getGraphicsContext2D();
		jfxFractalExplorer.getFractalScreen().getChildren().add(mouseCanvas);
		addMouseListners();
	}
	
	private void addMouseListners() {
		mouseCanvas.setOnMouseMoved(mouseHandler);
		mouseCanvas.setOnMousePressed(mouseHandler);
		mouseCanvas.setOnMouseDragged(mouseHandler);
		mouseCanvas.setOnMouseReleased(mouseHandler);
	}
	
	public void showOrbit(double x,double y) {
		double bigd = 100.0;
		double dx  = mandelbrotCanvas.getDX();
		double dy = mandelbrotCanvas.getDY();
		Point2D tl = mandelbrotCanvas.getTopLeft();
		Point2D br  =mandelbrotCanvas.getBottomRight();
		Point2D position = getPosition(x, y);
		double zx = position.getX();
		double zy = position.getY();
		
		ArrayList<Point2D> orbitPoints = new ArrayList<>();
		orbitPoints.add(new Point2D(x, y));
		for(int i = 0;i<pointsOnOrbit;i++) {
			double zx2 = zx*zx;
			double zy2 = zy*zy;
			if(Math.abs(zx2+zy2) > bigd) {
				break;
			}
			double newzx = zx2-zy2+position.getX();
			double newzy = 2.0*zx*zy+position.getY();
			zx = newzx;
			zy = newzy;
			
			if(zx >= tl.getX() &&
			   zx <= br.getX() &&
			   zy >= br.getY() &&
			   zy <= tl.getY()) {
				double px = (zx-tl.getX())/dx;
				double py = (tl.getY()-zy)/dy;
				orbitPoints.add(new Point2D(px, py));
			}
		}
		
		__drawOrbitPoints(orbitPoints);
	}
	
	private void __drawOrbitPoints(ArrayList<Point2D> orbitPoints) {
		mouseGC.clearRect(0, 0, FractalConstants.FRACTAL_DISPLAY_SIZE,FractalConstants.FRACTAL_DISPLAY_SIZE);
		for(Point2D p : orbitPoints) {
			double x = p.getX();
			double y = p.getY();
			mouseGC.setFill(Color.BLACK);
			mouseGC.fillRect(x-1, y-3, 3, 7);
			mouseGC.fillRect(x-3, y-1, 7, 3);
			mouseGC.setFill(Color.WHITE);
			mouseGC.fillRect(x, y-2, 1, 5);
			mouseGC.fillRect(x-2, y, 5, 1);
		}
	}
	
	public void doZoom(double x, double y,double zoomFactor) {
		System.out.println("doZoom " + x+":"+y+":"+zoomFactor);
	}
	
	public void showOrHideJuliaSet(double x, double y,boolean showJuliaSet) {
		if(showJuliaSet) {
			Point2D p = getPosition(x, y);	
			mandelbrotCanvas.setC(new ComplexNumber(p.getX(),p.getY()));	
		}
		
		mandelbrotCanvas.setJuliaSet(showJuliaSet);
		mandelbrotCanvas.draw();
	}
	
	public void undoZoom() {
		if(zoomUndoBuffer.size() == 0) {
			return;
		}
		MandelbrotCordinates mc = zoomUndoBuffer.pop();
		mandelbrotCanvas.setCoordinates(mc.getTopLeft(),mc.getBottomRight());
	}
	
	public class MouseHandler implements EventHandler<MouseEvent> {
		private double tlx,tly,brx,bry;
		private Point2D startPoint;
		private double dragSize = 0;
		private boolean dragged = false;
		
		@Override
		public void handle(MouseEvent event) {
			MandelbrotMouseActionType mouseAction = preference.getMouseActionType();
			if(event.getEventType().getName().equals("MOUSE_MOVED")) {
				updatePosition(event.getX(), event.getY());
				return;
			}
			
			if(event.getEventType().getName().equals("MOUSE_PRESSED")) {
				double x,y;
				x = event.getX();
				y = event.getY();
				if(x < 0) {
					x = 0;
				}
				if(x > mouseCanvas.getWidth()) {
					x = mouseCanvas.getWidth();
				}
				if(y < 0) {
					y = 0.0;
				}
				if(y > mouseCanvas.getHeight()) {
					y = mouseCanvas.getHeight();
				}
				startPoint = new Point2D(x, y);
				tlx = x;
				tly = y;
				
				if(preference.getMouseActionType() == MandelbrotMouseActionType.SHOW_ORBIT ||
						preference.getMouseActionType() == MandelbrotMouseActionType.SHOW_JULIA_SET	) {
					dragged = true;
					showOrbit(x, y);
				}
				
				return;
			}
			
			if(event.getEventType().getName().equals("MOUSE_RELEASED")) {
				mouseGC.clearRect(0, 0, mouseCanvas.getWidth(), mouseCanvas.getHeight());
				if(event.getButton() == MouseButton.PRIMARY) {
					if(event.getClickCount() == 2) {
						doZoom(event.getX(), event.getY(), 0.5);
					}else {
						if(!dragged) {
							return;
						}
						
						zoomUndoBuffer.push(new MandelbrotCordinates(mandelbrotCanvas.getTopLeft(), mandelbrotCanvas.getBottomRight()));
						if(mouseAction == MandelbrotMouseActionType.ZOOM_IN) {
							mandelbrotCanvas.setCoordinates(getPosition(tlx, tly,MandelbrotMouseActionType.ZOOM_IN),getPosition(brx, bry,MandelbrotMouseActionType.ZOOM_IN));
						}else if(mouseAction == MandelbrotMouseActionType.SHOW_JULIA_SET) {
							showOrHideJuliaSet(event.getX(), event.getY(),true);
						}
						
						dragged = false;
					}
				}else 	if(event.getButton() == MouseButton.SECONDARY) {
					if(mouseAction == MandelbrotMouseActionType.SHOW_JULIA_SET) {
						showOrHideJuliaSet(event.getX(), event.getY(),false);
					} else {
						undoZoom();
					}
				} 
				
				return;
			}
			if(event.getEventType().getName().equals("MOUSE_DRAGGED")) {
				dragged = true;
				double x = event.getX();
				double y = event.getY();
				if(mouseAction == MandelbrotMouseActionType.ZOOM_IN ) {
					double offsetX = Math.abs(x - startPoint.getX());
					double offsetY = Math.abs(y - startPoint.getY());
					dragSize = Math.max(offsetX, offsetY);
					
					if(x < tlx) {
						tlx = x;
					}
					if(y < tly) {
						tly = y;
					}
					
					brx = tlx+dragSize;
					bry = tly+dragSize;
					
					mouseGC.clearRect(0, 0, mouseCanvas.getWidth(), mouseCanvas.getHeight());
					mouseGC.setStroke(Color.BLACK);
					mouseGC.strokeRect(tlx, tly, dragSize, dragSize);
					mouseGC.setStroke(Color.WHITE);
					mouseGC.strokeRect(tlx-1, tly-1, dragSize+2, dragSize+2);
					mouseGC.strokeRect(tlx+1, tly+1, dragSize-2, dragSize-2);
					
					return;
				}
				if(mouseAction == MandelbrotMouseActionType.SHOW_ORBIT ||
						mouseAction == MandelbrotMouseActionType.SHOW_JULIA_SET	) {
					showOrbit(x, y);
				}
			}
		}
	}
}
