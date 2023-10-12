package jfx.fractal.explorer.drawing.mandelbrotset;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;

public class MandelbrotPreferencePane extends FractalDrawingPreferencePane {
	private TextField txtMAxIterations ;
	private TextField txtNumberOfColors;
	private ComboBox<MandelbrotMouseActionType> cmbMouseActionType;
	
	public MandelbrotPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Mandelbrot Set");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		MandelbrotPreference preference = MandelbrotPreference.getInstance();
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblIterations = new Label("Max Iteration");
			parametersPane.add(lblIterations, 0, 0);
			
			txtMAxIterations = new TextField(String.valueOf(preference.getMaxIterations()));
			parametersPane.add(txtMAxIterations, 1, 0);
			txtMAxIterations.setOnAction(e->{
				try {
					Integer maxIterations = Integer.parseInt(txtMAxIterations.getText());
					if(maxIterations < 1 || maxIterations > 2000) {
						jfxFractalExplorer.showErrorMessage("Max Iterations should be in range 1-2000");
						return;
					}
					preference.setMaxIterations(maxIterations);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			Label lblNumberOfColors = new Label("Number Of Colors");
			parametersPane.add(lblNumberOfColors, 0, 1);
			
			txtNumberOfColors = new TextField(String.valueOf(preference.getNumberOfColors()));
			parametersPane.add(txtNumberOfColors, 1, 1);
			txtNumberOfColors.setOnAction(e->{
				try {
					Integer numberOfColors = Integer.parseInt(txtNumberOfColors.getText());
					if(numberOfColors < 1 || numberOfColors > 2000) {
						jfxFractalExplorer.showErrorMessage("Number of colors should be in range 1-2000");
						return;
					}
					preference.setMaxIterations(numberOfColors);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			Label lblMouseActionType = new Label("Mouse Action Type");
			parametersPane.add(lblMouseActionType, 0, 2);
			
			cmbMouseActionType = new ComboBox<>(FXCollections.observableArrayList(MandelbrotMouseActionType.values()));
			parametersPane.add(cmbMouseActionType, 1,2);
			cmbMouseActionType.getSelectionModel().select(preference.getMouseActionType());
			cmbMouseActionType.setOnAction(e->{
				preference.setMouseActionType(cmbMouseActionType.getSelectionModel().getSelectedItem());
			});
		}
	}

	@Override
	public void disableControls() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableControls() {
		// TODO Auto-generated method stub

	}

}
