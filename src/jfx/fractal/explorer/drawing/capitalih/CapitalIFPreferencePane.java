package jfx.fractal.explorer.drawing.capitalih;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.drawing.vertexofsquare.VertexOfSquarePreference;
import jfx.fractal.explorer.preference.PenColorType;

public class CapitalIFPreferencePane extends FractalDrawingPreferencePane {
	private JFXFractalExplorer jfxFractalExplorer;
	private Spinner<Integer> spinnerIterations;
	private Spinner<Integer> spinnerSizePercent;
	private ComboBox<CapitalIHType> cmbFractalType;
	private ComboBox<PenColorType> cmbPenColorType;
	private CheckBox chkShowTurtle;
	
	public CapitalIFPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}
	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Capital IH Fractal");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		CapitalIHPreference preference = CapitalIHPreference.getInstance();
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblIterations = new Label("Ierations");
			parametersPane.add(lblIterations, 0, 0);
			
			spinnerIterations = new Spinner<>(0,CapitalIHPreference.MAX_ITERATIONS,preference.getIterations());
			spinnerIterations.setEditable(true);
			spinnerIterations.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setIterations(newValue);
				}
			});
			parametersPane.add(spinnerIterations, 1, 0);
		}
		
		{
			Label lblNumberOfCoplors = new Label("Size %");
			parametersPane.add(lblNumberOfCoplors, 0, 1);
			
			spinnerSizePercent = new Spinner<>(0,100,preference.getSizePercent());
			parametersPane.add(spinnerSizePercent, 1, 1);
			spinnerSizePercent.setEditable(true);
			spinnerSizePercent.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setSizePercent(newValue);
				}
			});
		}
		
		{
			Label lblFractalType = new Label("Fractal Type");
			parametersPane.add(lblFractalType, 0, 2);
			cmbFractalType = new ComboBox<>(FXCollections.observableArrayList(CapitalIHType.values()));
			cmbFractalType.getSelectionModel().select(preference.getType());
			cmbFractalType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CapitalIHType>() {
				@Override
				public void changed(ObservableValue<? extends CapitalIHType> observable, CapitalIHType oldValue,
						CapitalIHType newValue) {
					preference.setType(newValue);
				}
			});
			parametersPane.add(cmbFractalType, 1,2);
		}
		
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0, 3);
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.getSelectionModel().select(preference.getPenColorType());
			cmbPenColorType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PenColorType>() {
				@Override
				public void changed(ObservableValue<? extends PenColorType> observable, PenColorType oldValue,
						PenColorType newValue) {
					preference.setPenColorType(newValue);
				}
			});
			parametersPane.add(cmbPenColorType, 1,3);
		} 
		{
			chkShowTurtle = new CheckBox("Show Turtle");
			parametersPane.add(chkShowTurtle, 1, 4);
			chkShowTurtle.setSelected(preference.isShowTurtle());
			chkShowTurtle.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					preference.setShowTurtle(chkShowTurtle.isSelected());
				}
			});
		}
	}

	@Override
	public void disableControls() {
		spinnerIterations.setDisable(true);
		spinnerSizePercent.setDisable(true);
		cmbFractalType.setDisable(true);
		cmbPenColorType.setDisable(true);
		chkShowTurtle.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerIterations.setDisable(false);
		spinnerSizePercent.setDisable(false);
		cmbFractalType.setDisable(false);
		cmbPenColorType.setDisable(false);
		chkShowTurtle.setDisable(false);
	}

}
