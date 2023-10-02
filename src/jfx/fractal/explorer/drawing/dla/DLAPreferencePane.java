package jfx.fractal.explorer.drawing.dla;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.drawing.fracaltree.FracalTreePreference;
import jfx.fractal.explorer.preference.PenColorType;

public class DLAPreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerNumberOfColors;
	private Spinner<Integer> spinnerColorFactor;
	private Spinner<Integer> spinnerLaunchRow;
	private TextField txtRadius;
	private ComboBox<PenColorType> cmbPenColorType;
	private CheckBox chkSymmetric ;
	
	public DLAPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Difusion Limited Agregation");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		DLAPreference preference = DLAPreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane); 
		
		{
			Label lblNumberOfColors = new Label("Number Of Colors");
			parametersPane.add(lblNumberOfColors, 0, 0);
			
			spinnerNumberOfColors = new Spinner<>(1,500,preference.getNumberOfColors());
			spinnerNumberOfColors.setEditable(true);
			parametersPane.add(spinnerNumberOfColors, 1, 0);
			spinnerNumberOfColors.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setNumberOfColors(newValue);
				}
			});
		}
		
		{
			Label lblColorFactor = new Label("Color Factor");
			parametersPane.add(lblColorFactor, 0, 1);
			
			spinnerColorFactor = new Spinner<>(1,500,preference.getColorFactor());
			spinnerColorFactor.setEditable(true);
			parametersPane.add(spinnerColorFactor, 1, 1);
			spinnerColorFactor.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setColorFactor(newValue);
				}
			});
		}
		
		{
			Label lblLaunchRow = new Label("Launch Row");
			parametersPane.add(lblLaunchRow, 0, 2);
			
			spinnerLaunchRow = new Spinner<>(1,500,preference.getLaunchRow());
			spinnerLaunchRow.setEditable(true);
			parametersPane.add(spinnerLaunchRow, 1, 2);
			spinnerLaunchRow.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setLaunchRow(newValue);
				}
			});
		}
		
		{
			Label lblRadius = new Label("Radius");
			parametersPane.add(lblRadius, 0, 3);
			
			txtRadius = new TextField(String.valueOf(preference.getRadius()));
			parametersPane.add(txtRadius, 1, 3);
			txtRadius.setOnAction(e->{
				try {
					double raidus = Double.valueOf(txtRadius.getText());
					preference.setRadius(raidus);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0, 4);
			
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.getSelectionModel().select(preference.getPenColorType()); 
			parametersPane.add(cmbPenColorType, 1, 4);
			cmbPenColorType.setOnAction(e->{
				preference.setPenColorType(cmbPenColorType.getValue());
			});
		}
		
		{
			chkSymmetric = new CheckBox("Symmetric");
			parametersPane.add(chkSymmetric, 1, 5);
			chkSymmetric.setSelected(preference.isSymmetric());
			chkSymmetric.setOnAction(e->{
				preference.setSymmetric(chkSymmetric.isSelected());
			});
		}
	}

	@Override
	public void disableControls() {
		spinnerColorFactor.setDisable(true);
		spinnerLaunchRow.setDisable(true);
		spinnerNumberOfColors.setDisable(true);
		txtRadius.setDisable(true);
		cmbPenColorType.setDisable(true);
		chkSymmetric.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerColorFactor.setDisable(false);
		spinnerLaunchRow.setDisable(false);
		spinnerNumberOfColors.setDisable(false);
		txtRadius.setDisable(false);
		cmbPenColorType.setDisable(false);
		chkSymmetric.setDisable(false);
	}

}
