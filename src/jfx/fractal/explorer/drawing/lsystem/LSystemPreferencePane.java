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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;
import jfx.fractal.explorer.preference.PenColorType;
import jfx.fractal.explorer.preference.PreferenceManager;

public class LSystemPreferencePane extends FractalDrawingPreferencePane {
	private PreferenceManager preferenceManager = PreferenceManager.getInstance();
	
	private TextField txtIterations;
	private TextField txtLength;
	private TextField txtDelay;
	private TextField txtAxiom;
	private TextField txtStartX;
	private TextField txtStartY;
	private TextField txtLengthFactor;
	private TextField txtAngle;
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
		BorderPane rootPane = new BorderPane();
		rootPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		GridPane parametersPane = new GridPane();
		BorderPane rulesRootPane = new BorderPane();
		
		rootPane.setTop(parametersPane);
		rootPane.setCenter(rulesRootPane);
		GridPane rulesPane = new GridPane();
		
		rulesRootPane.setTop(rulesPane);
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		rulesPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(rootPane);
		
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
			Label lblLength = new Label("Length");
			parametersPane.add(lblLength, 0, 5);
			txtLength = new TextField();
			parametersPane.add(txtLength, 1, 5);
		}
		
		{
			Label lblStartX = new Label("Start X");
			parametersPane.add(lblStartX, 0, 6);
			txtStartX = new TextField();
			parametersPane.add(txtStartX, 1, 6);
		}
		
		{
			Label lblStartY = new Label("Start Y");
			parametersPane.add(lblStartY, 0, 7);
			txtStartY = new TextField();
			parametersPane.add(txtStartY, 1, 7);
		}
		
		{
			Label lblLengthFactor = new Label("Length Factor");
			parametersPane.add(lblLengthFactor, 0, 8);
			txtLengthFactor = new TextField();
			parametersPane.add(txtLengthFactor, 1, 8);
		}
		
		{
			Label lblAngle = new Label("Angle");
			parametersPane.add(lblAngle, 0, 9);
			txtAngle = new TextField();
			parametersPane.add(txtAngle, 1, 9);
		}
		
		{
			Label lblRules = new Label("Rules");
			rulesPane.add(lblRules, 0, 0);
			
			tableViewRules = new TableView<>();
			tableViewRules.setEditable(true);
			//tableViewRules.setFixedCellSize(0);
			tableViewRules.setPrefHeight(250.0);
			tableViewRules.setPrefWidth(500.0);
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
			columnReplacement.prefWidthProperty().bind(tableViewRules.widthProperty().multiply(0.7));
			
			tableViewRules.getColumns().addAll(columnKey,columnReplacement);
			
			rulesPane.add(tableViewRules, 1, 0);
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
		txtLength.setDisable(true);
		txtStartX.setDisable(true);
		txtStartY.setDisable(true);
		txtLengthFactor.setDisable(true);
		tableViewRules.setDisable(true);
		txtAngle.setDisable(true);
	}

	@Override
	public void enableControls() {
		cmbLSystems.setDisable(false);
		cmbPenColorType.setDisable(false);
		txtIterations.setDisable(false);
		txtAxiom.setDisable(false);
		txtLength.setDisable(false);
		txtStartX.setDisable(false);
		txtStartY.setDisable(false);
		txtLengthFactor.setDisable(false);
		tableViewRules.setDisable(false);
		txtAngle.setDisable(false);
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
		
		{
			txtLength.setOnAction(e -> {
				try {
					double length = Double.parseDouble(txtLength.getText());
					lSystemPreference.setLength(length);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			txtStartX.setOnAction(e->{
				try {
					double startX = Double.parseDouble(txtStartX.getText());
					lSystemPreference.setStartX(startX);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			txtStartY.setOnAction(e->{
				try {
					double startY = Double.parseDouble(txtStartY.getText());
					
					lSystemPreference.setStartY(startY);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			txtLengthFactor.setOnAction(e->{
				try {
					double lengthFactor = Double.parseDouble(txtLengthFactor.getText());
					
					lSystemPreference.setLengthFactor(lengthFactor);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			txtAngle.setOnAction(e->{
				try {
					double angle = Double.parseDouble(txtAngle.getText());
					
					lSystemPreference.setAngle(angle);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
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
	    txtLength.setText(String.valueOf(selecteLSystem.getLength()));
	    txtStartX.setText(String.valueOf(selecteLSystem.getStartX()));
	    txtStartY.setText(String.valueOf(selecteLSystem.getStartY()));
	    txtLengthFactor.setText(String.valueOf(selecteLSystem.getLengthFactor()));
	    txtAngle.setText(String.valueOf(selecteLSystem.getAngle()));
	}

}
