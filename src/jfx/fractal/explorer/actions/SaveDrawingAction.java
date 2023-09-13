package jfx.fractal.explorer.actions;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.resources.JFXResourceBundle;

public class SaveDrawingAction implements EventHandler<ActionEvent> {
	private JFXFractalExplorer jfxFractalExplorer;
	
	public SaveDrawingAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle(JFXResourceBundle.getString("jfx.fractal.explorer.saveDrawing.title"));
        File file = fileChooser.showSaveDialog(jfxFractalExplorer.getMainStage());
        
        if(file != null){
        	 try {
                 WritableImage writableImage = new WritableImage((int)FractalConstants.FRACTAL_DISPLAY_SIZE, 
                		 (int)FractalConstants.FRACTAL_DISPLAY_SIZE);
                 jfxFractalExplorer.getFractalScreen().snapshot(null, writableImage);
                 RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                 ImageIO.write(renderedImage, "png", file);
             } catch (IOException ex) {
            	 jfxFractalExplorer.showExceptionMessage(ex);
             }
        }
	}

}
