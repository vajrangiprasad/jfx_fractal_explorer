package jfx.fractal.explorer.drawing.squaretree;

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
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;
import jfx.fractal.explorer.resources.JFXResourceBundle;

public class SquareTreePreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Integer> spinnerIteration;
	private Spinner<Double> spinnerAnimationDelay;
	private ComboBox<PenColorType> cmbPenColorType ;
	private ComboBox<FillColorType> cmbFillColorType ;
	private ComboBox<SquareTreeAnimationType> cmbAnimationType;
	private CheckBox chkShowTurtle;
	
	public SquareTreePreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}
	
	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Square Tree");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		SquareTreePreference preference = SquareTreePreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane); 
		
		{
			Label lblIterations = new Label("Iterations");
			parametersPane.add(lblIterations, 0, 0);
			
			spinnerIteration = new Spinner<Integer>(1,50,preference.getIterations());
			parametersPane.add(spinnerIteration, 1, 0);
			spinnerIteration.setEditable(true);
			spinnerIteration.valueProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					preference.setIterations(newValue);
				}
			});
		}
		
		{
			Label lblAnimationDelay = new Label("Animation Delay");
			parametersPane.add(lblAnimationDelay, 0, 1);
			
			spinnerAnimationDelay= new Spinner<Double>(0.0,1.0,preference.getAnimationDelay(),0.05);
			parametersPane.add(spinnerAnimationDelay, 1, 1);
			spinnerAnimationDelay.setEditable(true);
			spinnerAnimationDelay.valueProperty().addListener(new ChangeListener<Double>() {
				@Override
				public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
					preference.setAnimationDelay(newValue);
				}
			});
		}
		 
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0,2);
			
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.getSelectionModel().select(preference.getPenColorType());
			parametersPane.add(cmbPenColorType, 1, 2);
			cmbPenColorType.valueProperty().addListener(new ChangeListener<PenColorType>() {
				@Override
				public void changed(ObservableValue<? extends PenColorType> observable, PenColorType oldValue,
						PenColorType newValue) {
					preference.setPenColorType(newValue);
				}
			});
		}
		
		{
			Label lblFillColorType = new Label("Fill Color Type");
			parametersPane.add(lblFillColorType, 0,3);
			
			cmbFillColorType = new ComboBox<>(FXCollections.observableArrayList(FillColorType.values()));
			cmbFillColorType.getSelectionModel().select(preference.getFillColorType());
			parametersPane.add(cmbFillColorType, 1, 3);
			cmbFillColorType.valueProperty().addListener(new ChangeListener<FillColorType>() {
				@Override
				public void changed(ObservableValue<? extends FillColorType> observable, FillColorType oldValue,
						FillColorType newValue) {
					preference.setFillColorType(newValue);
				}
			});
		}
		
		{
			Label lblAnimationType = new Label("Animation Typpe");
			parametersPane.add(lblAnimationType, 0,4);
			
			cmbAnimationType = new ComboBox<>(FXCollections.observableArrayList(SquareTreeAnimationType.values()));
			cmbAnimationType.getSelectionModel().select(preference.getAnimationType());
			parametersPane.add(cmbAnimationType, 1, 4);
			cmbAnimationType.valueProperty().addListener(new ChangeListener<SquareTreeAnimationType>() {
				@Override
				public void changed(ObservableValue<? extends SquareTreeAnimationType> observable, SquareTreeAnimationType oldValue,
						SquareTreeAnimationType newValue) {
					preference.setAnimationType(newValue);
				}
			});
		}
		
		{
			chkShowTurtle = new CheckBox("Show Turtle");
			parametersPane.add(chkShowTurtle, 1, 5);
			chkShowTurtle.setSelected(preference.showTurtle);
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
		spinnerIteration.setDisable(true);
		cmbPenColorType.setDisable(true);
		cmbAnimationType.setDisable(true);
		chkShowTurtle.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerIteration.setDisable(false);
		cmbPenColorType.setDisable(false);
		cmbAnimationType.setDisable(false);
		chkShowTurtle.setDisable(false);
	}

}
