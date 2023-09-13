package jfx.fractal.explorer;
	
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jfx.fractal.explorer.actions.ClearDrawingAction;
import jfx.fractal.explorer.actions.EditColorPreferenceAction;
import jfx.fractal.explorer.actions.ExitAction;
import jfx.fractal.explorer.actions.HelpAboutAction;
import jfx.fractal.explorer.actions.SaveDrawingAction;
import jfx.fractal.explorer.actions.TurtleTestDrawingAction;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.preference.PreferenceManager;
import jfx.fractal.explorer.resources.JFXResourceBundle;
import jfx.fractal.explorer.util.FractalUtility;


public class JFXFractalExplorer extends Application {
	private Stage mainStage;
	private Image fractalIcon;
	private Label lblStatusMessage;
	private ProgressBar progressBar;
	private StackPane fractalScreen;
	private ColorPreference colorPreference;
	private BorderPane settingsPane;
	private BorderPane controlPane;
	private IFractalDrawing fractalDrawing;
	private InvalidationListener colorPreferenceListner;
	
	private Button drawButton;
	private Button animateButton;
	private Button clearButton;
	private Button saveFractalButton;
	private Button saveSettingButton;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			colorPreference = PreferenceManager.getInstance().getColorPreference();
			
			colorPreferenceListner = new InvalidationListener() {
				@Override
				public void invalidated(Observable observable) {
					setBackgroundColor();
				}
			};
			
			colorPreference.addListener(colorPreferenceListner);
			
			this.mainStage = primaryStage;
						
			BorderPane root = new BorderPane();
			root.setTop(createMenuBar());
			root.setBottom(createStatusBar());
			root.setCenter(createFractalDisplayPane());
			root.setLeft(createSettingsPane());
			
			Scene scene = new Scene(root,1400,750);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			fractalIcon = new Image(getClass().getResourceAsStream("icons/fractal-icon.png"));
			primaryStage.getIcons().add(fractalIcon);
			primaryStage.setTitle(JFXResourceBundle.getString("jfx.fractal.explorer.title"));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			
			/*enableActionButton("draw", false);
			enableActionButton("animate", false);		*/
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getMainStage() {
		return mainStage;
	}

	public Image getFractalIcon() {
		return fractalIcon;
	}
	
	
	public StackPane getFractalScreen() {
		return fractalScreen;
	}

	
	public IFractalDrawing getFractalDrawing() {
		return fractalDrawing;
	}

	public void setFractalDrawing(IFractalDrawing fractalDrawing) {
		if(this.fractalDrawing != null) {
			this.fractalDrawing.clearDrawing();
			this.controlPane.getChildren().clear();
			this.fractalDrawing.dispose();
		}
		this.fractalDrawing = fractalDrawing;
		
		this.controlPane.setCenter(fractalDrawing.getControlNode());
	}

	public void showErrorMessage(String message) {
		showAlertMessage(message, AlertType.ERROR);
	}
	
	public void showWarningMessage(String message) {
		showAlertMessage(message, AlertType.WARNING);
	}
	
    public void showInfoMessage(String message) {
    	showAlertMessage(message, AlertType.INFORMATION);
	}
    
    public void showExceptionMessage(Exception ex) {
    	Dialog<ButtonType> messageDialog = new Dialog<>();
		messageDialog.setTitle(JFXResourceBundle.getString("jfx.fractal.explorer.title"));
		Stage stage = (Stage) messageDialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(fractalIcon);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		
		Image alerticonImage = new Image(getClass().getResourceAsStream("icons/error.png"));
		ImageView alertIconView = new ImageView(alerticonImage);
		grid.add(alertIconView, 0, 0);
		
		Label lblMessage = new Label(ex.getMessage());
		grid.add(lblMessage, 1, 0);
		
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();
        
		TextArea exceptionStackTraceArea = new TextArea(exceptionText); 
		exceptionStackTraceArea.setEditable(false);
		exceptionStackTraceArea.setWrapText(true);
		grid.add(exceptionStackTraceArea, 1, 1);
		
		
		messageDialog.getDialogPane().setContent(grid);
		ButtonType okType = new ButtonType("OK", ButtonData.OK_DONE);
		messageDialog.getDialogPane().getButtonTypes().add(okType );
		
		messageDialog.showAndWait();
    }
	
    public void updateStatusMessage(String statusMessage) {
    	lblStatusMessage.setText(statusMessage);
    }
    
    public void updateProgress(double value) {
    	progressBar.setProgress(value);
    }
    
    private void showAlertMessage(String message,AlertType type) {
    	Dialog<ButtonType> messageDialog = new Dialog<>();
		messageDialog.setTitle(JFXResourceBundle.getString("jfx.fractal.explorer.title"));
		Stage stage = (Stage) messageDialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(fractalIcon);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		
		String alertIcon = "";
		switch(type) {
			case CONFIRMATION:
				alertIcon = "info.png";
				break;
			case ERROR:
				alertIcon = "error.png";
				break;
			case INFORMATION:
				alertIcon = "info.png";
				break;
			case NONE:
				alertIcon = "";
				break;
			case WARNING:
				alertIcon = "warning.png";
				break;
		}
		
		if(!alertIcon.isEmpty()) {;
			Image alerticonImage = new Image(getClass().getResourceAsStream("icons/"+alertIcon));
			ImageView alertIconView = new ImageView(alerticonImage);
			grid.add(alertIconView, 0, 0);
		}
		
		Label lblMessage = new Label(message);
		grid.add(lblMessage, 1, 0);
		
		messageDialog.getDialogPane().setContent(grid);
		
		ButtonType okType = new ButtonType("OK", ButtonData.OK_DONE);
		messageDialog.getDialogPane().getButtonTypes().add(okType );
		
		messageDialog.showAndWait();
    }
    
	private MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();
		
		createFractalsMenu(menuBar);
		createEditMenu(menuBar);
		createHelpMenu(menuBar);
		
		return menuBar;
	}
	
	private void createFractalsMenu(MenuBar menuBar) {
		Menu fractalsMenu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals"));
		fractalsMenu.setMnemonicParsing(true);
		
		createAnimationMenu(fractalsMenu);
		createComplexNumberFractalsMenu(fractalsMenu);
		createDifferentialEquationMenu(fractalsMenu);
		createFractalsMenu(fractalsMenu);
		createGenerativeArtMenu(fractalsMenu);
		createIllutionsArtMenu(fractalsMenu);
		createSimulationMenu(fractalsMenu);
		createTesselationMenu(fractalsMenu);
		createTurtleGamesMenu(fractalsMenu);
		createTurtleGraphicsMenu(fractalsMenu);
		createSeperatorMenuItem(fractalsMenu);
		createExitMenuItem(fractalsMenu);
		
		menuBar.getMenus().add(fractalsMenu);
	}
	
	private void createAnimationMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.animation"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createComplexNumberFractalsMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.complexNumberFractals"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createDifferentialEquationMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.differentialEquation"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createFractalsMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.fractals"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createGenerativeArtMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.generativeArt"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createIllutionsArtMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.illutions"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createSimulationMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.simulation"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createTesselationMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.tesselation"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createTurtleGamesMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.turtlegames"));
		menu.setMnemonicParsing(true);
		fractalsMenu.getItems().add(menu);
	}
	
	private void createTurtleGraphicsMenu(Menu fractalsMenu) {
		Menu menu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.turtlegraphics"));
		menu.setMnemonicParsing(true);
		
		MenuItem menuItemTurtleTestDrawing = new MenuItem(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.turtlegraphics.testTurtleDrawing"));
		menuItemTurtleTestDrawing.setMnemonicParsing(true);
		menuItemTurtleTestDrawing.setOnAction(new TurtleTestDrawingAction(this));
		menu.getItems().add(menuItemTurtleTestDrawing);
		
		fractalsMenu.getItems().add(menu);
	}
	
	private void createSeperatorMenuItem(Menu parentMenu) {
		parentMenu.getItems().add(new SeparatorMenuItem());
	} 
	
	private void createExitMenuItem(Menu parentMenu) {
		MenuItem exitMenuItem = new MenuItem(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.fractals.exit"));
		exitMenuItem.setMnemonicParsing(true);
		exitMenuItem.setOnAction(new ExitAction());
		parentMenu.getItems().add(exitMenuItem);
	}
	
	private void createEditMenu(MenuBar menuBar) {
		Menu editMenu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.edit"));
		editMenu.setMnemonicParsing(true);
		
		createEditColorPreferenceMenu(editMenu);
		
		menuBar.getMenus().add(editMenu);
	}
	
	private void createEditColorPreferenceMenu(Menu editMenu) {
		MenuItem editColorPrefereneMenu = new MenuItem(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.edit.colorPreference"));
		editColorPrefereneMenu.setMnemonicParsing(true);
		editColorPrefereneMenu.setOnAction(new EditColorPreferenceAction(this));
		editMenu.getItems().add(editColorPrefereneMenu);
	}
	
	private void createHelpMenu(MenuBar menuBar) {
		Menu helpMenu = new Menu(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.help"));
		helpMenu.setMnemonicParsing(true);
		
		MenuItem aboutJfxFractalExplorer = new MenuItem(JFXResourceBundle.getString("jfx.fractal.explorer.menuBar.help.about"));
		aboutJfxFractalExplorer.setMnemonicParsing(true);
		aboutJfxFractalExplorer.setOnAction(new HelpAboutAction(this));
		helpMenu.getItems().add(aboutJfxFractalExplorer);
		
		menuBar.getMenus().add(helpMenu);
	}
	
	private BorderPane createStatusBar() {
		BorderPane statusPane = new BorderPane();
		statusPane.setStyle("-fx-border-color:black;-fx-padding: 5;");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-hgap:2;-fx-vgap:2;-fx-alignment:center-left;-fx-padding:2");
        
        lblStatusMessage = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.version"));
        lblStatusMessage.setPrefWidth(700);
		grid.add(lblStatusMessage, 0, 0);
		
		progressBar = new ProgressBar(0);
		
		progressBar.setPrefWidth(700);
		grid.add(progressBar, 1, 0);
		
		statusPane.setCenter(grid);
		
		return statusPane;
	}
	
	private GridPane createFractalDisplayPane() {
		GridPane centerPane = new GridPane();
		centerPane.setPrefHeight(FractalConstants.FRACTAL_DISPLAY_SIZE+20.0);
		centerPane.setPrefWidth(FractalConstants.FRACTAL_DISPLAY_SIZE+20);
		centerPane.setStyle("-fx-hgap:2;-fx-vgap:2;-fx-alignment:center;-fx-padding:2");
		
		fractalScreen = new StackPane();
		fractalScreen.setPrefHeight(FractalConstants.FRACTAL_DISPLAY_SIZE);
		fractalScreen.setPrefWidth(FractalConstants.FRACTAL_DISPLAY_SIZE);
		setBackgroundColor();
		centerPane.add(fractalScreen, 0, 0);
		
		return centerPane;
	}
	
	private BorderPane createSettingsPane() {
		settingsPane = new BorderPane();
		settingsPane.setPrefHeight(FractalConstants.FRACTAL_DISPLAY_SIZE+20.0);
		settingsPane.setPrefWidth(FractalConstants.FRACTAL_DISPLAY_SIZE+20);
		settingsPane.setStyle("-fx-border-color:black");
		settingsPane.setBottom(createActionToolBar());
		
		controlPane = new BorderPane();
		settingsPane.setCenter(controlPane);
		
		return settingsPane;
	}
	
	private void setBackgroundColor() {
		fractalScreen.setStyle("-fx-background-color:"+FractalUtility.getWebColor(colorPreference.getBackgroundColor())+";");
	}
	
	private ToolBar createActionToolBar() {
		ToolBar toolBar = new ToolBar();
		toolBar.setPrefSize(FractalConstants.FRACTAL_DISPLAY_SIZE, 40);
		toolBar.setStyle("-fx-border-color:black");
		BorderPane.setAlignment(toolBar, Pos.CENTER);
		
		drawButton = new Button(JFXResourceBundle.getString("jfx.fractal.explorer.draw.text"),getImageView("icons/draw.png"));
		drawButton.setMnemonicParsing(true);
		toolBar.getItems().add(drawButton);
		
		animateButton = new Button(JFXResourceBundle.getString("jfx.fractal.explorer.animate.text"),getImageView("icons/animation.png"));
		animateButton.setMnemonicParsing(true);
		toolBar.getItems().add(animateButton);
		
		clearButton = new Button(JFXResourceBundle.getString("jfx.fractal.explorer.clear.text"),getImageView("icons/clear.png"));
		clearButton.setMnemonicParsing(true);
		clearButton.setOnAction(new ClearDrawingAction(this));
		toolBar.getItems().add(clearButton);
		
		saveFractalButton = new Button(JFXResourceBundle.getString("jfx.fractal.explorer.saveFractal.text"),getImageView("icons/save.png"));
		saveFractalButton.setMnemonicParsing(true);
		toolBar.getItems().add(saveFractalButton);
		saveFractalButton.setOnAction(new SaveDrawingAction(this));
		
		saveSettingButton = new Button(JFXResourceBundle.getString("jfx.fractal.explorer.saveSetting.text"),getImageView("icons/save.png"));
		saveSettingButton.setMnemonicParsing(true);
		toolBar.getItems().add(saveSettingButton);
		
		return toolBar;
	}
	
	public  ImageView getImageView(String url) {
		Image image = new Image(getClass().getResourceAsStream(url));
		
		return new ImageView(image);
	}
}
