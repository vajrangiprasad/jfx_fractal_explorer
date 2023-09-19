package jfx.fractal.explorer.drawing.templefractal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class TempleFractalPreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerRadiusPercent;
	private Spinner<Integer> spinnerNumberOfColors;
	private Spinner<Double> spinnerDelay;
	private TextField textFieldDirection ;
	private ComboBox<PenColorType> cmbPenColorType;
	private ComboBox<FillColorType> cmbFillColorType;
	private CheckBox chkDrawFull;
	
	private TempleFractalPreference preference = TempleFractalPreference.getInstance();
	
	public TempleFractalPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Temple Fracal");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		TempleFractalPreference preference = TempleFractalPreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblRadiusPercent = new Label("Radius (%)");
			parametersPane.add(lblRadiusPercent, 0, 0);
			
			spinnerRadiusPercent = new Spinner<>(0,100,preference.getRadiusPercent());
			spinnerRadiusPercent.setEditable(true);
			parametersPane.add(spinnerRadiusPercent, 1, 0);
			spinnerRadiusPercent.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setRadiusPercent(newValue);
				}
			});
		}
		
		{
			Label lblNumberOfCoplors = new Label("Number Of Colors");
			parametersPane.add(lblNumberOfCoplors, 0, 1);
			
			spinnerNumberOfColors = new Spinner<>(0,TempleFractalPreference.MAX_COLORS,preference.getNumberOfColors());
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
			Label lblDelay = new Label("Delay");
			parametersPane.add(lblDelay, 0, 2);
			spinnerDelay = new Spinner<>();
			spinnerDelay.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,
					1.0, 
					preference.getDelay(), 
					0.05));
			parametersPane.add(spinnerDelay, 1,2);
			spinnerDelay.valueProperty().addListener(new ChangeListener<Double>() {
				@Override
				public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
					preference.setDelay(newValue);
				}
			});
		}
		
		{
			Label lblDirection = new Label("Direction (Degrees)");
			parametersPane.add(lblDirection, 0, 3);
			textFieldDirection = new TextField(String.valueOf(preference.getDirection()));
			parametersPane.add(textFieldDirection, 1, 3);
			textFieldDirection.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					double direction = 0.0;
					try {
						direction = Double.parseDouble(textFieldDirection.getText());
					}catch(Exception ex) {
						jfxFractalExplorer.showExceptionMessage(ex);
						return;
					}
					preference.setDirection(direction);
				}
			});
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
		
		{
			chkDrawFull = new CheckBox("Draww Full");
			chkDrawFull.setSelected(preference.isDrawFull());
			chkDrawFull.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					preference.setDrawFull(chkDrawFull.isSelected());
				}
			});
			parametersPane.add(chkDrawFull, 1, 6);
		}
	}

	@Override
	public void disableControls() {
		spinnerRadiusPercent.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerRadiusPercent.setDisable(false);
	}

}
