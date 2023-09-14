package jfx.fractal.explorer.drawing.gardi;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.preference.PenColorType;
import jfx.fractal.explorer.resources.JFXResourceBundle;

public class GardiFractalPreferencePane extends BorderPane {
	private JFXFractalExplorer jfxFractalExplorer;
	private GardiFractalPreference gardiFractalPreference = GardiFractalPreference.getInstance();
	private TextField txtRadius;
	private Spinner<Integer> spinnerIteration;
	
	public GardiFractalPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		initialize();
	}
	
	private void initialize() {
		setStyle("-fx-padding:5;");
		createHeaderPane();
		createParametersPane();
	}
	
	private void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.drawing.gardi.lblDrawing"));
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}
	
	private void createParametersPane() {
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblRadius = new Label("Radius");
			parametersPane.add(lblRadius, 0, 0);
			txtRadius = new TextField(String.valueOf(gardiFractalPreference.getRadius()));
			txtRadius.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						double r = Double.parseDouble(txtRadius.getText());
						if(r > GardiFractalPreference.MAX_RADIUS) {
							jfxFractalExplorer.showErrorMessage("Radius should be between 1.0 and " + GardiFractalPreference.MAX_RADIUS);
							return;
						}
						if(r < 1.0) {
							jfxFractalExplorer.showErrorMessage("Radius should be between 1.0 and " + GardiFractalPreference.MAX_RADIUS);
							return;
						}
						gardiFractalPreference.setRadius(r);
					}catch(Exception e) {
						jfxFractalExplorer.showExceptionMessage(e);
					}
				}
			});
			parametersPane.add(txtRadius, 1, 0);
		}
		
		{
			Label lblIterations = new Label("Iterations");
			parametersPane.add(lblIterations, 0, 1);
			spinnerIteration = new Spinner<>(1, GardiFractalPreference.MAX_ITERATION, gardiFractalPreference.getIterations());
			parametersPane.add(spinnerIteration, 1, 1);
			spinnerIteration.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					gardiFractalPreference.setIterations(newValue);
				}
			});
		}
		
		{
			Label lblOrientation = new Label("Orientation");
			parametersPane.add(lblOrientation, 0, 2);
			ComboBox<GardiFractalOrientation> cmbOrientation = new ComboBox<>(FXCollections.observableArrayList(GardiFractalOrientation.values()));
			cmbOrientation.getSelectionModel().select(gardiFractalPreference.getOrientation());
			cmbOrientation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GardiFractalOrientation>() {
				@Override
				public void changed(ObservableValue<? extends GardiFractalOrientation> observable,
						GardiFractalOrientation oldValue, GardiFractalOrientation newValue) {
					gardiFractalPreference.setOrientation(newValue);
				}
			});
			parametersPane.add(cmbOrientation, 1, 2);
		}
		
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0, 3);
			ComboBox<PenColorType> cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.getSelectionModel().select(gardiFractalPreference.getPenColorType());
			cmbPenColorType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PenColorType>() {
				@Override
				public void changed(ObservableValue<? extends PenColorType> observable, PenColorType oldValue,
						PenColorType newValue) {
					gardiFractalPreference.setPenColorType(newValue);
				}
			});
			parametersPane.add(cmbPenColorType, 1, 3);
			
		}
	}
}
