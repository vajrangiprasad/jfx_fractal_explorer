package jfx.fractal.explorer.drawing.dla;


import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.drawing.canvas.FractalCanvas;
import jfx.fractal.explorer.preference.ColorPreference;

public class DLAFractalRenderTask extends FractalDrawingRenderTask {
	private FractalCanvas fractalCanvas;
	private DLAPreference dlaPreference = DLAPreference.getInstance();
	private int particles = 0;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Color[] paletteColors;
	private Color[] rainbowColors;
	
	public DLAFractalRenderTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,FractalCanvas fractalCanvas ) {
		super(fractalExplorer, taskType);
		this.fractalCanvas = fractalCanvas;
	}

	@Override
	public void draw() {
		dlaPreference.setJobCanceled(false);
		updateMessage("Rendering Diffusion Limed Agrgation Fractal ...");
		updateProgress(0, 100);
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(dlaPreference.getNumberOfColors(), 0);
		rainbowColors = ColorPreference.createRainbowColors(dlaPreference.getNumberOfColors());
		if(dlaPreference.isSymmetric()) {
			drawSymmetricDLA();
		}else {
			drawDLA();
		}
	}

	@Override
	public void animate() {
		
	}
	
	private void drawSymmetricDLA() {
		int n = (int)fractalCanvas.getWidth();
		int x, y;                            
        double radius = dlaPreference.getRadius();                  
        double dist;                         
        boolean[][] dla = new boolean[n][n];  
        particles = 0;
        
        dla[n/2][n/2] = true;
        while (radius < (n/2 - 2)) {
        	double angle = 2.0 * Math.PI * Math.random();
            x = (int) (n/2.0 + radius * Math.cos(angle));
            y = (int) (n/2.0 + radius * Math.sin(angle));
            
            while (true) {
                double r = Math.random();
                if      (r < 0.25) x--;
                else if (r < 0.50) x++;
                else if (r < 0.75) y++;
                else               y--;
 
                dist = Math.sqrt((n/2-x)*(n/2-x) + (n/2-y)*(n/2-y));
                if (dist >= Math.min((n-2)/2.0, radius + 25)) break;

                
                if (dla[x-1][y]   || dla[x+1][y]   || dla[x][y-1]   || dla[x][y+1]   ||
                    dla[x-1][y-1] || dla[x+1][y+1] || dla[x-1][y+1] || dla[x+1][y-1] ) {
                    dla[x][y] = true;
                    if (dist > radius) radius = dist;
                    break;
                }
            }

            if (dla[x][y]) {
                particles++;
                setPenColor();
                fractalCanvas.drawPixel(x, n - y - 1);
            }
        }
	}
	
	private void drawDLA() {
		int n = (int)fractalCanvas.getWidth();
		int launch = n - dlaPreference.getLaunchRow();
		boolean dla[][] = new boolean[n][n];
		particles = 0;
		for (int x = 0; x < n; x++) {
			dla[x][0] = true;
		}

		boolean done = false;
		while (!done && !dlaPreference.isJobCanceled()) {
			int x = (int) (n * Math.random());
			int y = launch;
			while (x < n - 2 && x > 1 && y < n - 2 && y > 1) {
				double r = Math.random();
				if (r < 0.25) {
					x--;
				} else if (r < 0.50) {
					x++;
				} else if (r < 0.65) {
					y++;
				} else {
					y--;
				}

				if (dla[x - 1][y] || dla[x + 1][y] || dla[x][y - 1] || dla[x][y + 1] || dla[x - 1][y - 1]
						|| dla[x + 1][y + 1] || dla[x - 1][y + 1] || dla[x + 1][y - 1]) {
					dla[x][y] = true;
					particles++;
					setPenColor();
					fractalCanvas.drawPixel(x, n - y - 1);
										
					if (y > launch) {
						done = true;
					}

					break;
				}
			}
		}
	}
	
	private void setPenColor() {
		Color penColor = colorPreference.getPenColor();
		
		switch(dlaPreference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(particles / dlaPreference.getColorFactor() % dlaPreference.getNumberOfColors(), 
						dlaPreference.getNumberOfColors(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[particles / dlaPreference.getColorFactor() % dlaPreference.getNumberOfColors()];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[particles / dlaPreference.getColorFactor() % dlaPreference.getNumberOfColors()];
			break;
		case RANDOM_COLR:
			penColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_PEN_COLOR:
			penColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			penColor = particles%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		fractalCanvas.setPenColor(penColor);
	}
}
