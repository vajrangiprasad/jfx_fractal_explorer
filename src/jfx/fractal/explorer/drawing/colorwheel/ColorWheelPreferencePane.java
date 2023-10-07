package jfx.fractal.explorer.drawing.colorwheel;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.preference.FillColorType;

public class ColorWheelPreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerNumberOfColors;
	private Spinner<Double> spinnerRotation;
	private Spinner<Integer> spinnerRadiusPercent;
	private Spinner<Double> spinnerDelay;
	private ComboBox<FillColorType> cmbColorType;
	
	public ColorWheelPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Color Wheel");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		ColorWheelPreference preference = ColorWheelPreference.getInstance();
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane); 
		
		StringConverter<Double> doubleConverter = new StringConverter<Double>() {
			private final DecimalFormat df = new DecimalFormat("###.###");
			@Override
			public String toString(Double object) {
				if (object == null) {
					return "";
				}
			    return df.format(object);
			}
			
			@Override
			public Double fromString(String string) {
				try {
					if (string == null) {
						return null;
				    }
				    string = string.trim();
				    if (string.length() < 1) {
				    	return null;
				    }     
				    return df.parse(string).doubleValue();
				} catch (ParseException ex) {
				    throw new RuntimeException(ex);
				}    
			}
		};
		
		{
			Label lblNumberOfColors = new Label("Number Of Colors");
			parametersPane.add(lblNumberOfColors, 0, 0);
			
			spinnerNumberOfColors = new Spinner<>(1,360,preference.getNumberOfColors());
			spinnerNumberOfColors.setEditable(true);
			parametersPane.add(spinnerNumberOfColors, 1, 0);
			spinnerNumberOfColors.valueProperty().addListener(listenr -> {
				preference.setNumberOfColors(spinnerNumberOfColors.getValue());
			});
		}
		
		{
			Label lblRotation = new Label("Rotation");
			parametersPane.add(lblRotation, 0, 1);
			
			spinnerRotation = new Spinner<>(0.0,360,preference.getRotation(),0.1);
			spinnerRotation.setEditable(true);
			parametersPane.add(spinnerRotation, 1, 1);
			spinnerRotation.getValueFactory().setConverter(doubleConverter);
			spinnerRotation.valueProperty().addListener(listenr -> {
				preference.setRotation(spinnerRotation.getValue());
			});
		}
		
		{
			Label lblRadiusPercent = new Label("Radius %");
			parametersPane.add(lblRadiusPercent, 0, 2);
			
			spinnerRadiusPercent = new Spinner<>(1,100,preference.getRadiusPercent());
			spinnerRadiusPercent.setEditable(true);
			parametersPane.add(spinnerRadiusPercent, 1, 2);
			spinnerRadiusPercent.valueProperty().addListener(listenr -> {
				preference.setRadiusPercent(spinnerRadiusPercent.getValue());
			});
		}
		
		{
			Label lblDelay = new Label("Delay");
			parametersPane.add(lblDelay, 0, 3);
			
			spinnerDelay = new Spinner<>(0.0,1.0,preference.getTimerDelay(),0.001);
			spinnerDelay.setEditable(true);
			parametersPane.add(spinnerDelay, 1, 3);
			spinnerDelay.getValueFactory().setConverter(doubleConverter);
			spinnerDelay.valueProperty().addListener(listenr -> {
				preference.setTimerDelay(spinnerDelay.getValue());
			});
		}
		
		{
			Label lblColorType = new Label("Color Type");
			parametersPane.add(lblColorType, 0, 4);
			
			cmbColorType = new ComboBox<>(FXCollections.observableArrayList(FillColorType.values()));
			cmbColorType.getSelectionModel().select(preference.getColorType());
			parametersPane.add(cmbColorType, 1, 4);
			cmbColorType.setOnAction(e->{
				preference.setColorType(cmbColorType.getValue());
			});
		}
	}

	@Override
	public void disableControls() {
		spinnerNumberOfColors.setDisable(true);
		spinnerRadiusPercent.setDisable(true);
		cmbColorType.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerNumberOfColors.setDisable(false);
		spinnerRadiusPercent.setDisable(false);
		cmbColorType.setDisable(false);
	}

}
