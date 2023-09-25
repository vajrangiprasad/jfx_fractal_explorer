package jfx.fractal.explorer.drawing.fracaltree;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.preference.PenColorType;
import jfx.fractal.explorer.resources.JFXResourceBundle;

public class FractalTreePreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerIteration;
	private Spinner<Double> spinnerStemWidth;
	private Spinner<Double> spinnerAngle;
	private ComboBox<PenColorType> cmbPenColorType ;
	
	public FractalTreePreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}
	
	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Fractal Tree");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		FracalTreePreference preference = FracalTreePreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane); 
		
		{
			Label lblIterations = new Label("Iterations");
			parametersPane.add(lblIterations, 0, 0);
			
			spinnerIteration = new Spinner<Integer>(1,50,preference.getIterations());
			parametersPane.add(spinnerIteration, 1, 0);
			spinnerIteration.setEditable(true);
			spinnerIteration.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setIterations(newValue);
				}
			});
		}
		
		{
			Label lblStemWidth = new Label("Stem Width");
			parametersPane.add(lblStemWidth, 0, 1);
			
			spinnerStemWidth = new Spinner<Double>(1.0,50.0,preference.getStemWidth(),0.5);
			parametersPane.add(spinnerStemWidth, 1, 1);
			spinnerStemWidth.setEditable(true);
			spinnerStemWidth.valueProperty().addListener(new ChangeListener<Double>() {
				@Override
				public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
					preference.setStemWidth(newValue);
				}
			});
		}
		
		{
			Label lblAngle = new Label("Angle");
			parametersPane.add(lblAngle, 0, 2);
			
			spinnerAngle= new Spinner<Double>(1.0,50.0,preference.getAngle(),0.5);
			parametersPane.add(spinnerAngle, 1, 2);
			spinnerAngle.setEditable(true);
			spinnerAngle.valueProperty().addListener(new ChangeListener<Double>() {
				@Override
				public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
					preference.setAngle(newValue);
				}
			});
		}
		 
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0,3);
			
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.getSelectionModel().select(preference.getPenColorType());
			parametersPane.add(cmbPenColorType, 1, 3);
			cmbPenColorType.valueProperty().addListener(new ChangeListener<PenColorType>() {
				@Override
				public void changed(ObservableValue<? extends PenColorType> observable, PenColorType oldValue,
						PenColorType newValue) {
					preference.setPenColorType(newValue);
				}
			});
		}
	}

	@Override
	public void disableControls() {
		spinnerIteration.setDisable(true);
		spinnerAngle.setDisable(true);
		spinnerStemWidth.setDisable(true);
		cmbPenColorType.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerIteration.setDisable(false);
		spinnerAngle.setDisable(false);
		spinnerStemWidth.setDisable(false);
		cmbPenColorType.setDisable(false);
	}

}
