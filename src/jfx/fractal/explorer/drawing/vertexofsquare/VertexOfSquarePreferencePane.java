package jfx.fractal.explorer.drawing.vertexofsquare;

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
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.drawing.templefractal.TempleFractalPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class VertexOfSquarePreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerSizePercent;
	private Spinner<Integer> spinnerNumberOfColors;
	private TextField txtSizeDelta;
	private TextField txtAngleDelta;
	private ComboBox<PenColorType> cmbPenColorType;
	private ComboBox<FillColorType> cmbFillColorType;
	
	public VertexOfSquarePreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}
	
	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Vertex Of Square");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		VertexOfSquarePreference preference = VertexOfSquarePreference.getInstance();
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblSizePercent = new Label("Size (%)");
			parametersPane.add(lblSizePercent, 0, 0);
			spinnerSizePercent = new Spinner<>(0,100,preference.getSizePercent());
			spinnerSizePercent.setEditable(true);
			spinnerSizePercent.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setSizePercent(newValue);
				}
			});
			parametersPane.add(spinnerSizePercent, 1, 0);
		}
		
		{
			Label lblNumberOfCoplors = new Label("Number Of Colors");
			parametersPane.add(lblNumberOfCoplors, 0, 1);
			
			spinnerNumberOfColors = new Spinner<>(0,VertexOfSquarePreference.MAX_COLORS,preference.getNumberOfColors());
			parametersPane.add(spinnerNumberOfColors, 1, 1);
			spinnerNumberOfColors.setEditable(true);
			spinnerNumberOfColors.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setNumberOfColors(newValue);
				}
			});
		}
		
		{
			Label lblSizeDelta = new Label("Size Delta");
			parametersPane.add(lblSizeDelta, 0, 2);
			txtSizeDelta = new TextField(String.valueOf(preference.getSizeDelta()));
			txtSizeDelta.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					double sizeDelta = 0.0;
					try {
						sizeDelta = Double.parseDouble(txtSizeDelta.getText());
					}catch(Exception ex) {
						jfxFractalExplorer.showExceptionMessage(ex);
						return;
					}
					preference.setSizeDelta(sizeDelta);
				}
			});
			parametersPane.add(txtSizeDelta, 1, 2);
		}
		
		{
			Label lblAngleDelta = new Label("Angle Delta");
			parametersPane.add(lblAngleDelta, 0, 3);
			txtAngleDelta = new TextField(String.valueOf(preference.getAngleDelta()));
			txtAngleDelta.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					double angleDelta = 0.0;
					try {
						angleDelta = Double.parseDouble(txtAngleDelta.getText());
					}catch(Exception ex) {
						jfxFractalExplorer.showExceptionMessage(ex);
						return;
					}
					preference.setAngleDelta(angleDelta);
				}
			});
			parametersPane.add(txtAngleDelta, 1,3);
		}
		
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0, 4);
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.getSelectionModel().select(preference.getPenColorType());
			cmbPenColorType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PenColorType>() {
				@Override
				public void changed(ObservableValue<? extends PenColorType> observable, PenColorType oldValue,
						PenColorType newValue) {
					preference.setPenColorType(newValue);
				}
			});
			parametersPane.add(cmbPenColorType, 1,4);
		}
		
		{
			Label lblFillColorType = new Label("Fill Color Type");
			parametersPane.add(lblFillColorType, 0, 5);
			cmbFillColorType = new ComboBox<>(FXCollections.observableArrayList(FillColorType.values()));
			cmbFillColorType.getSelectionModel().select(preference.getFillColorType());
			cmbFillColorType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FillColorType>() {
				@Override
				public void changed(ObservableValue<? extends FillColorType> observable, FillColorType oldValue,
						FillColorType newValue) {
					preference.setFillColorType(newValue);
				}
			});
			parametersPane.add(cmbFillColorType, 1,5);
		}
	}

	@Override
	public void disableControls() {
		spinnerNumberOfColors.setDisable(true);
		spinnerSizePercent.setDisable(true);
		txtAngleDelta.setDisable(true);
		txtSizeDelta.setDisable(true);
		cmbFillColorType.setDisable(true);
		cmbPenColorType.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerNumberOfColors.setDisable(false);
		spinnerSizePercent.setDisable(false);
		txtAngleDelta.setDisable(false);
		txtSizeDelta.setDisable(false);
		cmbFillColorType.setDisable(false);
		cmbPenColorType.setDisable(false);
	}
}
