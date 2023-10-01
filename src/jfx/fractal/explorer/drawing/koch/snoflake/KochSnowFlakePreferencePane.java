package jfx.fractal.explorer.drawing.koch.snoflake;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class KochSnowFlakePreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerIterations;
	private Spinner<Integer> spinnerSizePercent;
	private ComboBox<KochSnowFlakeType> cmbType;
	private ComboBox<PenColorType> cmbPenColorType;
	
	public KochSnowFlakePreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Koch Snow Flake");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		KochSnowFlakePreference preference = KochSnowFlakePreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblIterations = new Label("Iterations");
			parametersPane.add(lblIterations, 0, 0);
			
			spinnerIterations = new Spinner<>(1,KochSnowFlakePreference.MAX_LEVEL,preference.getIterations());
			parametersPane.add(spinnerIterations, 1, 0);
			spinnerIterations.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setIterations(newValue);
				}
			});
		}
		
		{
			Label lblSizePercent = new Label("Size %");
			parametersPane.add(lblSizePercent, 0, 1);
			
			spinnerSizePercent = new Spinner<>(1,100,preference.getSizePercent());
			parametersPane.add(spinnerSizePercent, 1, 1);
			spinnerSizePercent.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setSizePercent(newValue);
				}
			});
		}
		
		{
			Label lblType = new Label("Type");
			parametersPane.add(lblType, 0, 2);
			
			cmbType = new ComboBox<>(FXCollections.observableArrayList(KochSnowFlakeType.values()));
			parametersPane.add(cmbType, 1, 2);
			cmbType.getSelectionModel().select(preference.getType());
			cmbType.setOnAction(e->{
				preference.setType(cmbType.getSelectionModel().getSelectedItem());
			});
		}
		
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0, 3);
			
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			parametersPane.add(cmbPenColorType, 1, 3);
			cmbPenColorType.getSelectionModel().select(preference.getPenColorType());
			cmbPenColorType.setOnAction(e->{
				preference.setPenColorType(cmbPenColorType.getSelectionModel().getSelectedItem());
			});
		}
	}

	@Override
	public void disableControls() {
		spinnerIterations.setDisable(true);
		spinnerSizePercent.setDisable(true);
		cmbType.setDisable(true);
		cmbPenColorType.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerIterations.setDisable(false);
		spinnerSizePercent.setDisable(false);
		cmbType.setDisable(false);
		cmbPenColorType.setDisable(false);
	}

}
