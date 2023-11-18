package jfx.fractal.explorer.drawing.island;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.resources.JFXResourceBundle;

public class FractalIslandPreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerSizePercent;
	private Spinner<Integer> spinnerEdgeWidth;
	private ColorPicker edgeColorPicker;
	private ColorPicker fillColorPicker;
	private ColorPicker backgrundColorPicker;
	
	public FractalIslandPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Fractal Island");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		FractalIslandPreference preference = FractalIslandPreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		{
			Label lblsizePercent = new Label("Size Percent");
			parametersPane.add(lblsizePercent, 0, 0);
			
			spinnerSizePercent = new Spinner<>(3,360,preference.getSizePercent());
			spinnerSizePercent.setEditable(true);
			parametersPane.add(spinnerSizePercent, 1, 0);
			spinnerSizePercent.valueProperty().addListener(listner -> {
				preference.setSizePercent(spinnerSizePercent.getValue());
			});
		}
		
		{
			Label lblEdgeWidth = new Label("Edge Width");
			parametersPane.add(lblEdgeWidth, 0, 1);
			
			spinnerEdgeWidth = new Spinner<>(3,360,preference.getEdgeWidth());
			spinnerEdgeWidth.setEditable(true);
			parametersPane.add(spinnerEdgeWidth, 1, 1);
			spinnerEdgeWidth.valueProperty().addListener(listner -> {
				preference.setEdgeWidth(spinnerEdgeWidth.getValue());
			});
		}
		
		{
			Label lblEdgeColor = new Label("Edge Color");
			parametersPane.add(lblEdgeColor, 0, 2);
			
			edgeColorPicker = new ColorPicker(preference.getEdgeColor());
			edgeColorPicker.setOnAction(e -> {
				preference.setEdgeColor(edgeColorPicker.getValue());
			});
			parametersPane.add(edgeColorPicker, 1, 2);
		}
		
		{
			Label lblFillColor = new Label("Fill Color");
			parametersPane.add(lblFillColor, 0, 3);
			
			fillColorPicker = new ColorPicker(preference.getFillColor());
			fillColorPicker.setOnAction(e -> {
				preference.setFillColor(fillColorPicker.getValue());
			});
			parametersPane.add(fillColorPicker, 1, 3);
		}
		
		{
			Label lblBackgroundColor = new Label("Background Color");
			parametersPane.add(lblBackgroundColor, 0, 4);
			
			backgrundColorPicker = new ColorPicker(preference.getBackgroundColor());
			backgrundColorPicker.setOnAction(e -> {
				preference.setBackgroundColor(backgrundColorPicker.getValue());
			});
			parametersPane.add(backgrundColorPicker, 1, 4);
		}
	}

	@Override
	public void disableControls() {
		spinnerEdgeWidth.setDisable(true);
		spinnerSizePercent.setDisable(true);
		edgeColorPicker.setDisable(true);
		fillColorPicker.setDisable(true);
		backgrundColorPicker.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerEdgeWidth.setDisable(false);
		spinnerSizePercent.setDisable(false);
		edgeColorPicker.setDisable(false);
		fillColorPicker.setDisable(false);
		backgrundColorPicker.setDisable(false);
	}

}
