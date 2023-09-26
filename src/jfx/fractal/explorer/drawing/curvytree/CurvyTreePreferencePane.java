package jfx.fractal.explorer.drawing.curvytree;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;

public class CurvyTreePreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerLengthPercent;
	private Spinner<Integer> spinnerAngle1;
	private Spinner<Integer> spinnerAngle2;
	private ColorPicker stemColorPicker;
	private ColorPicker leafColorPicker;
	private Spinner<Double> spinnerDelay;
	private CheckBox chkShowTurtle;
	private ComboBox<CurvyTreeAnimationType> cmbAnimationType;
	
	public CurvyTreePreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Curvy Tree");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		CurvyTreePreference preference = CurvyTreePreference.getInstance();
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane); 
		
		{
			Label lblLengthPercent = new  Label("Length %");
			parametersPane.add(lblLengthPercent, 0, 0);
			
			spinnerLengthPercent = new Spinner<Integer>(1, CurvyTreePreference.MAX_LENGTH, preference.getLengthPercent());
			spinnerLengthPercent.setEditable(true);
			parametersPane.add(spinnerLengthPercent, 1, 0);
			spinnerLengthPercent.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setLengthPercent(newValue);
				}
			});
		}
		
		{
			Label lblAngle1 = new  Label("Angle 1");
			parametersPane.add(lblAngle1, 0, 1);
			
			spinnerAngle1 = new Spinner<Integer>(1, CurvyTreePreference.MAX_LENGTH, preference.getAngle1());
			spinnerAngle1.setEditable(true);
			parametersPane.add(spinnerAngle1, 1, 1);
			spinnerAngle1.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setAngle1(newValue);
				}
			});
		}
		
		{
			Label lblAngle2 = new  Label("Angle 2");
			parametersPane.add(lblAngle2, 0, 2);
			
			spinnerAngle2 = new Spinner<Integer>(1, CurvyTreePreference.MAX_LENGTH, preference.getAngle2());
			spinnerAngle2.setEditable(true);
			parametersPane.add(spinnerAngle2, 1, 2);
			spinnerAngle2.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setAngle2(newValue);
				}
			});
		}
		
		{
			Label lblAnimationDelay = new  Label("Animation Delay");
			parametersPane.add(lblAnimationDelay, 0, 3);
			
			spinnerDelay= new Spinner<Double>(0.0, 1.0, preference.getAnimationDelay());
			spinnerDelay.setEditable(true);
			parametersPane.add(spinnerDelay, 1, 3);
			spinnerDelay.valueProperty().addListener(new ChangeListener<Double>() {
				@Override
				public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
					preference.setAnimationDelay(newValue);
				}
			});

		}
		
		{
			Label lblStemColor = new Label("Stem Color");
			parametersPane.add(lblStemColor, 0, 4);
			
			stemColorPicker = new ColorPicker(preference.getStemColor());
			parametersPane.add(stemColorPicker, 1, 4);
			stemColorPicker.setOnAction(e -> {
				preference.setStemColor(stemColorPicker.getValue());
			});
		}
		
		{
			Label lblLeafColor = new Label("Leaf Color");
			parametersPane.add(lblLeafColor, 0, 5);
			
			leafColorPicker = new ColorPicker(preference.getLeafColor());
			parametersPane.add(leafColorPicker, 1, 5);
			leafColorPicker.setOnAction(e -> {
				preference.setLeafColor(leafColorPicker.getValue());
			});
		}
		
		{
			Label lblAnimationType = new Label("Animation Type");
			parametersPane.add(lblAnimationType, 0, 6);
			
			cmbAnimationType = new ComboBox<CurvyTreeAnimationType>(FXCollections.observableArrayList(CurvyTreeAnimationType.values()));
			parametersPane.add(cmbAnimationType, 1, 6);
			cmbAnimationType.getSelectionModel().select(preference.getAnimationType());
			cmbAnimationType.setOnAction(e -> {
				preference.setAnimationType(cmbAnimationType.getSelectionModel().getSelectedItem());
			});
		}
		
		{
			chkShowTurtle = new CheckBox("Show Turtle");
			parametersPane.add(chkShowTurtle, 1, 7);
			chkShowTurtle.setSelected(preference.isShowTurtle());
			chkShowTurtle.setOnAction(e -> {
				preference.setShowTurtle(chkShowTurtle.isSelected());
			});
		}
	}

	@Override
	public void disableControls() {
		spinnerAngle1.setDisable(true);
		spinnerAngle2.setDisable(true);
		spinnerDelay.setDisable(true);
		spinnerLengthPercent.setDisable(true);
		stemColorPicker.setDisable(true);
		leafColorPicker.setDisable(true);
		cmbAnimationType.setDisable(true);
		chkShowTurtle.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerAngle1.setDisable(false);
		spinnerAngle2.setDisable(false);
		spinnerDelay.setDisable(false);
		spinnerLengthPercent.setDisable(false);
		stemColorPicker.setDisable(false);
		leafColorPicker.setDisable(false);
		cmbAnimationType.setDisable(false);
		chkShowTurtle.setDisable(false);
	}

}
