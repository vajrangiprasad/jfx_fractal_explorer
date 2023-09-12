package jfx.fractal.explorer.drawing.turtletest;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.resources.JFXResourceBundle;
import jfx.fractal.explorer.turtle.Turtle;

public class TurtleTestControlPanel extends BorderPane{
	private JFXFractalExplorer jfxFractalExplorer;
	private TurtleTestPreference turtleTestPreference = TurtleTestPreference.getInstance();
	private Turtle turtle;
	private TextField txtStepSize;
	private TextField txtAngle;
	private Button btnBeginFill ;
	private Button btnEndFill;
	
	public TurtleTestControlPanel(JFXFractalExplorer jfxFractalExplorer,Turtle turtle) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		this.turtle = turtle;
		initialize();
	}
	
	private void initialize() {
		setStyle("-fx-padding:5;");
		
		createHeaderPane();
		createParametersPane();
		createButtonsPane();
		
	}
	
	private void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		Label lblDrawing = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.drawing.turtletest.lblDrawing"));
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}
	
	private void createParametersPane() {
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		Label lblStepSize = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.drawing.turtletest.lblStepSize"));
		parametersPane.add(lblStepSize, 0, 0);
		txtStepSize = new TextField(String.valueOf(turtleTestPreference.getStepSize()));
		parametersPane.add(txtStepSize, 1, 0);
		txtStepSize.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				turtleTestPreference.setStepSize(Double.parseDouble(txtStepSize.getText()));
			}
		});
		
		Label lblAngle = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.drawing.turtletest.lblAngle"));
		parametersPane.add(lblAngle, 0, 1);
		txtAngle = new TextField(String.valueOf(turtleTestPreference.getAngle()));
		parametersPane.add(txtAngle, 1, 1);
		txtAngle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				turtleTestPreference.setAngle(Double.parseDouble(txtAngle.getText()));
			}
		});
		
		CheckBox penStatus = new CheckBox("Pen Down");
		parametersPane.add(penStatus, 0, 2);
		penStatus.setSelected(true);
		penStatus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CheckBox c = (CheckBox)event.getSource();
				if(c.isSelected()) {
					turtle.penDown();
				}else {
					turtle.penUp();
				}
			}
		});
	}
	
	private void createButtonsPane() {
		GridPane buttonsPane = new GridPane();
		buttonsPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setBottom(buttonsPane);
		
		Button backwardButton = new Button("Backward");
		buttonsPane.add(backwardButton, 0, 0);
		backwardButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				turtle.backward(turtleTestPreference.getStepSize());
			}
		});
		Button forwardButton = new Button("Forward");
		buttonsPane.add(forwardButton, 1, 0);
		forwardButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				turtle.forward(turtleTestPreference.getStepSize());
			}
		});
		
		Button turnLeftButton = new Button("Turn Left");
		buttonsPane.add(turnLeftButton, 0, 1);
		turnLeftButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				turtle.left(turtleTestPreference.getAngle());
			}
		});
		Button turnRightButton = new Button("Turn Right");
		buttonsPane.add(turnRightButton, 1, 1);
		turnRightButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				turtle.right(turtleTestPreference.getAngle());
			}
		});
		
		btnBeginFill = new  Button("Begin Fill");
		buttonsPane.add(btnBeginFill, 0, 2);
		
		btnEndFill = new Button("End Fill");
		buttonsPane.add(btnEndFill, 1, 2);
		
		btnEndFill.setDisable(true);
		
		btnBeginFill.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				turtle.beginFilling();
				btnEndFill.setDisable(false);
				btnBeginFill.setDisable(true);
			}
		});
		
		btnEndFill.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				turtle.endFilling();
				btnEndFill.setDisable(true);
				btnBeginFill.setDisable(false);
			}
		});
	}
}
