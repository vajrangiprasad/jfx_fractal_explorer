package jfx.fractal.explorer.drawing.lsystem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.preference.PenColorType;
import jfx.fractal.explorer.preference.PreferenceManager;

public class LSystemPreferencePane extends FractalDrawingPreferencePane {
	private PreferenceManager preferenceManager = PreferenceManager.getInstance();
	
	private TextField txtIterations;
	private TextField txtDelay;
	private TextField txtAxiom;
	private ComboBox<PenColorType> cmbPenColorType;
	private ComboBox<LSystem> cmbLSystems;
	
	private TableView tableViewRules;
	
	public LSystemPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("LSystem");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		LSystemPrefereence lSystemPrefereence = LSystemPrefereence.getInstance();
		PreferenceManager preferenceManager = PreferenceManager.getInstance();
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblDely = new Label("Delay");
			parametersPane.add(lblDely, 0, 0);
			
			txtDelay = new TextField(String.valueOf(lSystemPrefereence.getDelay()));
			parametersPane.add(txtDelay, 1, 0);
			txtDelay.setOnAction(e -> {
				try {
					double delay = Double.valueOf(txtDelay.getText());
					lSystemPrefereence.setDelay(delay);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			Label lblPenColorType = new Label("Pen Color Type");
			parametersPane.add(lblPenColorType, 0, 1);
			
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.getSelectionModel().select(lSystemPrefereence.getPenColorType());
			cmbPenColorType.valueProperty().addListener(new ChangeListener<PenColorType>() {
				@Override
				public void changed(ObservableValue<? extends PenColorType> observable, PenColorType oldValue,
						PenColorType newValue) {
					lSystemPrefereence.setPenColorType(newValue);
				}
			});
			parametersPane.add(cmbPenColorType, 1, 1);
		}
		
		{
			Label lblSelectLSysem = new Label("Select L-Sysem");
			parametersPane.add(lblSelectLSysem, 0, 2);
			
			cmbLSystems = new ComboBox<>(preferenceManager.getLSystems());
			parametersPane.add(cmbLSystems, 1, 2);
			cmbLSystems.getSelectionModel().select(lSystemPrefereence.getSelectedLSystem());
		}
		
		{
			Label lblAxiom = new Label("Axiom");
			parametersPane.add(lblAxiom, 0, 3);
			
			txtAxiom = new TextField();
			parametersPane.add(txtAxiom, 1, 3);
		}
		
		{
			Label lblIterations = new Label("Iterations");
			parametersPane.add(lblIterations, 0, 4);
			txtIterations = new TextField();
			parametersPane.add(txtIterations, 1, 4);
		}
		
		{
			Label lblRules = new Label("Rules");
			parametersPane.add(lblRules, 0, 5);
			
			tableViewRules = new TableView<>();
			tableViewRules.setEditable(true);
			tableViewRules.setFixedCellSize(30);
			tableViewRules.setPrefHeight(200.0);
			TableColumn<LSystemRule, String> columnKey = new TableColumn<>("Key");
			columnKey.setCellValueFactory(new PropertyValueFactory<>("key"));
			columnKey.setCellFactory(TextFieldTableCell.<LSystemRule>forTableColumn());
			columnKey.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LSystemRule,String>>() {
				@Override
				public void handle(CellEditEvent<LSystemRule, String> cell) {
					LSystemRule rule = cell.getTableView().getItems().get(cell.getTablePosition().getRow());
					rule.setKey(cell.getNewValue());
				}
			});
			columnKey.setResizable(false);
			columnKey.prefWidthProperty().bind(tableViewRules.widthProperty().multiply(0.3));
			
			TableColumn<LSystemRule, String> columnReplacement = new TableColumn<>("Replacement Rule");
			columnReplacement.setCellValueFactory(new PropertyValueFactory<>("replacement"));
			columnReplacement.setCellFactory(TextFieldTableCell.<LSystemRule>forTableColumn());
			columnReplacement.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LSystemRule,String>>() {
				@Override
				public void handle(CellEditEvent<LSystemRule, String> cell) {
					LSystemRule rule = cell.getTableView().getItems().get(cell.getTablePosition().getRow());
					rule.setReplacement(cell.getNewValue());
				}
			});
			columnReplacement.setResizable(false);
			columnReplacement.prefWidthProperty().bind(tableViewRules.widthProperty().multiply(0.65));
			
			tableViewRules.getColumns().addAll(columnKey,columnReplacement);
			
			parametersPane.add(tableViewRules, 1, 5);
		}
	
		updateControlValues();
		addListners();
	}

	@Override
	public void disableControls() {
		cmbLSystems.setDisable(true);
		cmbPenColorType.setDisable(true);
		txtIterations.setDisable(true);
		txtAxiom.setDisable(true);
	}

	@Override
	public void enableControls() {
		cmbLSystems.setDisable(false);
		cmbPenColorType.setDisable(false);
		txtIterations.setDisable(false);
		txtAxiom.setDisable(false);
	}
	
	private void addListners() {
		LSystemPrefereence lSystemPreference = LSystemPrefereence.getInstance();
		cmbLSystems.setOnAction(e -> {
			LSystem selecteLSystem = cmbLSystems.getSelectionModel().getSelectedItem();
			lSystemPreference.setSelectedLSystem(selecteLSystem);
			updateControlValues();
		}); 
		
		{
			txtIterations.setOnAction(e->{
				try {
					int iterations = Integer.parseInt(txtIterations.getText());
					lSystemPreference.setIterations(iterations);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
				
			});
		}
		
		{
			txtAxiom.setOnAction(e->{
				lSystemPreference.setAxiom(txtAxiom.getText());
			});
		}
	}
	
	private void updateControlValues() {
		LSystemPrefereence lSystemPreference = LSystemPrefereence.getInstance();
		LSystem selecteLSystem = lSystemPreference.getSelectedLSystem();
		txtIterations.setText(String.valueOf(selecteLSystem.getIterations()));
		txtAxiom.setText(selecteLSystem.getAxiom());
		tableViewRules.getItems().clear();
	    tableViewRules.getItems().addAll(selecteLSystem.getRules());
	}

}
