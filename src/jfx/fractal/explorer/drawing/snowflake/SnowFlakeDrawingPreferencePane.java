package jfx.fractal.explorer.drawing.snowflake;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.preference.PenColorType;

public class SnowFlakeDrawingPreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerLengthPercent;
	private Spinner<Integer> spinnerIteration;
	private Spinner<Double> spinnerDelay;
	private TextField txtFieldAngle;
	private TextField txtFieldPenSize;
	private ComboBox<PenColorType> cmbPenColorType ;
	public SnowFlakeDrawingPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Snow Flake");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		SnowFlakeDrawingPreference preference = SnowFlakeDrawingPreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblLength = new Label("Length %");
			parametersPane.add(lblLength, 0, 0);
			spinnerLengthPercent = new Spinner<>(0,100,preference.getLengthPercent());
			parametersPane.add(spinnerLengthPercent, 1, 0);
			spinnerLengthPercent.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setLengthPercent(newValue);
				}
			});
		}
		
		{
			Label lblAngle = new Label("Angle in Degrees");
			parametersPane.add(lblAngle, 0, 1);
			txtFieldAngle = new TextField(String.valueOf(preference.getAngle()));
			txtFieldAngle.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					double angle = 0.0;
					try {
						angle = Double.parseDouble(txtFieldAngle.getText());
					}catch(Exception ex) {
						jfxFractalExplorer.showExceptionMessage(ex);
						return;
					}
					preference.setAngle(angle);
				}
			});
			parametersPane.add(txtFieldAngle, 1, 1);
		}
		 
		{
			Label lblIteration = new Label("Iterations");
			parametersPane.add(lblIteration, 0, 2);
			spinnerIteration = new Spinner<>(0,100,preference.getIteration());
			parametersPane.add(spinnerIteration, 1, 2);
			spinnerIteration.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setIteration(newValue);
				}
			});
		}
		
		{
			Label lblPenSize = new Label("Pen Size");
			parametersPane.add(lblPenSize, 0, 3);
			txtFieldPenSize = new TextField(String.valueOf(preference.getPenSize()));
			txtFieldPenSize.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					double penSize = 0.0;
					try {
						penSize = Double.parseDouble(txtFieldPenSize.getText());
					}catch(Exception ex) {
						jfxFractalExplorer.showExceptionMessage(ex);
						return;
					}
					preference.setPenSize(penSize);
				}
			});
			parametersPane.add(txtFieldPenSize, 1,3);
		}
		
		{
			Label lblDelay = new Label("Delay");
			parametersPane.add(lblDelay, 0, 4);
			spinnerDelay = new Spinner<>();
			spinnerDelay.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,
					1.0, 
					preference.getDelay(), 
					0.05));
			parametersPane.add(spinnerDelay, 1,4);
			spinnerDelay.valueProperty().addListener(new ChangeListener<Double>() {
				@Override
				public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
					preference.setDelay(newValue);
				}
			});
		}
		
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0, 5);
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.getSelectionModel().select(preference.getPenColorType());
			cmbPenColorType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PenColorType>() {
				@Override
				public void changed(ObservableValue<? extends PenColorType> observable, PenColorType oldValue,
						PenColorType newValue) {
					preference.setPenColorType(newValue);
				}
			});
			parametersPane.add(cmbPenColorType, 1,5);
		}
	}

	@Override
	public void disableControls() {
		spinnerLengthPercent.setDisable(true);
		txtFieldAngle.setDisable(true);
		txtFieldPenSize.setDisable(true);
		cmbPenColorType.setDisable(true);
		spinnerIteration.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerLengthPercent.setDisable(false);
		txtFieldAngle.setDisable(false);
		txtFieldPenSize.setDisable(false);
		cmbPenColorType.setDisable(false);
		spinnerIteration.setDisable(false);
	}
	
}
