package jfx.fractal.explorer.drawing.polygon;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.AnimationType;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class PolygonPreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerSides;
	private Spinner<Integer> spinnerSizePercent;
	private Spinner<Double> spinnerSizeDelta;
	private Spinner<Double> spinnerRotation;
	private Spinner<Double> spinnerAngleDelta;
	private Spinner<Double> spinnerDelay;
	private ComboBox<PenColorType> cmbPenColorType;
	private ComboBox<FillColorType> cmbFillColorType;
	private ComboBox<AnimationType> cmbAnimationType;
	private CheckBox chkConnectVertices;
	
	public PolygonPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Polygon");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		PolygonPreference preference = PolygonPreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		StringConverter<Double> doubleConverter = new StringConverter<Double>() {
			private final DecimalFormat df = new DecimalFormat("###.##");
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
			Label lblSides = new Label("Sides");
			parametersPane.add(lblSides, 0, 0);
			
			spinnerSides = new Spinner<>(3,360,preference.getSides());
			spinnerSides.setEditable(true);
			parametersPane.add(spinnerSides, 1, 0);
			spinnerSides.valueProperty().addListener(listner -> {
				preference.setSides(spinnerSides.getValue());
			});
		}
		
		{
			Label lblSizePercent = new Label("Size %");
			parametersPane.add(lblSizePercent, 0, 1);
			
			spinnerSizePercent = new Spinner<>(3,360,preference.getSizePercent());
			spinnerSizePercent.setEditable(true);
			parametersPane.add(spinnerSizePercent, 1, 1);
			spinnerSizePercent.valueProperty().addListener(listner -> {
				preference.setSizePercent(spinnerSizePercent.getValue());
			});
		}
		
		{
			Label lblRotation = new Label("Rotation in Degrees");
			parametersPane.add(lblRotation, 0, 2);
			
			spinnerRotation = new Spinner<>(0.0,360.0,preference.getRotation(),1.0);
			spinnerRotation.setEditable(true);
			parametersPane.add(spinnerRotation, 1, 2);
			spinnerRotation.valueProperty().addListener(listner -> {
				preference.setRotation(spinnerRotation.getValue());
			});
		}
		
		{
			Label lblSizeDelta = new Label("Size Delta %");
			parametersPane.add(lblSizeDelta, 0, 3);
			
			spinnerSizeDelta = new Spinner<>(0.01,100.0,preference.getSizeDelta(),0.01);
			spinnerSizeDelta.setEditable(true);
			parametersPane.add(spinnerSizeDelta, 1, 3);
			spinnerSizeDelta.valueProperty().addListener(listner -> {
				preference.setSizeDelta(spinnerSizeDelta.getValue());
			});
			spinnerSizeDelta.getValueFactory().setConverter(doubleConverter);
		}
		
		{
			Label lblAngleDelta = new Label("Angle Delta");
			parametersPane.add(lblAngleDelta, 0, 4);
			
			spinnerAngleDelta = new Spinner<>(0.01,360.0,preference.getAngleDelta(),0.01);
			spinnerAngleDelta.setEditable(true);
			parametersPane.add(spinnerAngleDelta, 1, 4);
			spinnerAngleDelta.valueProperty().addListener(listner -> {
				preference.setAngleDelta(spinnerAngleDelta.getValue());
			});
			spinnerAngleDelta.getValueFactory().setConverter(doubleConverter);
		}
		
		{
			Label lblDelay = new Label("Delay");
			parametersPane.add(lblDelay, 0, 5);
			
			spinnerDelay = new Spinner<>(0.0,1.0,preference.getDelay(),0.01);
			spinnerDelay.setEditable(true);
			parametersPane.add(spinnerDelay, 1, 5);
			spinnerDelay.valueProperty().addListener(listner -> {
				preference.setDelay(spinnerDelay.getValue());
			});
			spinnerDelay.getValueFactory().setConverter(doubleConverter);
		}
		
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0, 6);
			
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			parametersPane.add(cmbPenColorType, 1, 6);
			cmbPenColorType.getSelectionModel().select(preference.getPenColorType());
			cmbPenColorType.setOnAction(e->{
				preference.setPenColorType(cmbPenColorType.getSelectionModel().getSelectedItem());
			});
		}
		
		{
			Label lblFillColorType = new Label("Fill Color Type");
			parametersPane.add(lblFillColorType, 0, 7);
			
			cmbFillColorType = new ComboBox<>(FXCollections.observableArrayList(FillColorType.values()));
			parametersPane.add(cmbFillColorType, 1, 7);
			cmbFillColorType.getSelectionModel().select(preference.getFillColorType());
			cmbFillColorType.setOnAction(e->{
				preference.setFillColorType(cmbFillColorType.getSelectionModel().getSelectedItem());
			});
		}
		
		{
			Label lblAnimationType = new Label("Animation Type");
			parametersPane.add(lblAnimationType, 0, 8);
			
			cmbAnimationType = new ComboBox<>(FXCollections.observableArrayList(AnimationType.values()));
			parametersPane.add(cmbAnimationType, 1, 8);
			cmbAnimationType.getSelectionModel().select(preference.getAnimationType());
			cmbAnimationType.setOnAction(e->{
				preference.setAnimationType(cmbAnimationType.getSelectionModel().getSelectedItem());
			});
		} 
		
		{
			chkConnectVertices = new CheckBox();
			chkConnectVertices.setSelected(preference.isConnectVertices());
			parametersPane.add(chkConnectVertices, 1, 9);
			chkConnectVertices.setOnAction(e->{
				preference.setConnectVertices(chkConnectVertices.isSelected());
			});
		}
	}

	@Override
	public void disableControls() {
		spinnerSides.setDisable(true);
		spinnerAngleDelta.setDisable(true);
		spinnerRotation.setDisable(true);
		spinnerSizeDelta.setDisable(true);
		spinnerSizePercent.setDisable(true);
		cmbAnimationType.setDisable(true);
		cmbFillColorType.setDisable(true);
		cmbPenColorType.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerSides.setDisable(false);
		spinnerAngleDelta.setDisable(false);
		spinnerRotation.setDisable(false);
		spinnerSizeDelta.setDisable(false);
		spinnerSizePercent.setDisable(false);
		cmbAnimationType.setDisable(false);
		cmbFillColorType.setDisable(false);
		cmbPenColorType.setDisable(false);
	}

}
