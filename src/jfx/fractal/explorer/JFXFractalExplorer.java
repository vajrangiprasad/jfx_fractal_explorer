package jfx.fractal.explorer;
	
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jfx.fractal.explorer.actions.AnimateAction;
import jfx.fractal.explorer.actions.ClearDrawingAction;
import jfx.fractal.explorer.actions.DrawAction;
import jfx.fractal.explorer.actions.EditColorPreferenceAction;
import jfx.fractal.explorer.actions.ExitAction;
import jfx.fractal.explorer.actions.HelpAboutAction;
import jfx.fractal.explorer.actions.SaveDrawingAction;
import jfx.fractal.explorer.actions.SaveSettingsAction;
import jfx.fractal.explorer.actions.StopRenderingAction;
import jfx.fractal.explorer.actions.TurtleTestDrawingAction;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.drawing.capitalih.CapitalIHAction;
import jfx.fractal.explorer.drawing.curvytree.CurvyTreeAction;
import jfx.fractal.explorer.drawing.dla.DLAFractalAction;
import jfx.fractal.explorer.drawing.fracaltree.FractalTreeAction;
import jfx.fractal.explorer.drawing.gardi.GardiFracalDrawingAction;
import jfx.fractal.explorer.drawing.koch.snoflake.KochSnowFlakeAction;
import jfx.fractal.explorer.drawing.lsystem.LSystemFractalAction;
import jfx.fractal.explorer.drawing.plasma.PlasmaFractalAction;
import jfx.fractal.explorer.drawing.snowflake.SnowFlakeDrawingAction;
import jfx.fractal.explorer.drawing.squaretree.SquareTreeAction;
import jfx.fractal.explorer.drawing.templefractal.TempleFractalDrawingAction;
import jfx.fractal.explorer.drawing.vertexofsquare.VertexOfSquareAction;
import jfx.fractal.explorer.drawing.vertexofsquare.VertexOfSquareDrawing;
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
	private Button cancelButton;
	private Button clearButton;
	private Button saveFractalButton;
	private Button saveSettingButton;
	
	private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    
    private double  splashWidth ;
    private double splashHeight;
    
	@Override
	public void init() throws Exception {
		fractalIcon = new Image(getClass().getResourceAsStream("icons/fractal-icon.png"));
		ImageView splash = new ImageView(new Image(getClass().getResourceAsStream("icons/fractal-splash.png")));

		Bounds bounds = splash.getLayoutBounds();
		splashWidth = bounds.getWidth();
		splashHeight = bounds.getHeight();
		loadProgress = new ProgressBar();
		loadProgress.setPrefWidth(splashWidth);
		progressText = new Label("Loading JavaFX Fractal Explorer version 1.0 .....");
		splashLayout = new VBox();
		splashLayout.getChildren().addAll(splash, loadProgress, progressText);

		progressText.setAlignment(Pos.CENTER);
		splashLayout.setStyle("-fx-padding: 5;");
		splashLayout.setEffect(new DropShadow());
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		final Task<Void> spalshTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				updateMessage("Loading JavaFX Fractal Explorer version 1.0 .....");
				for(int i = 0 ;i<100;i++) {
					 updateProgress(i + 1, 100);
					 Thread.sleep(10);
				}
				Thread.sleep(400);
				return null;
			}
		};
		
		showSplash(
				primaryStage,
				spalshTask,
                () -> showMainStage()
        );
        new Thread(spalshTask).start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void enableControls() {
		if(fractalDrawing != null) {
			fractalDrawing.enableControls();
		}
		drawButton.setDisable(false);
		animateButton.setDisable(false);
		clearButton.setDisable(false);
		cancelButton.setDisable(true);
	}
	
	public void disableControls() {
		if(fractalDrawing != null) {
			fractalDrawing.disableControls();
		}
		drawButton.setDisable(true);
		animateButton.setDisable(true);
		clearButton.setDisable(true);
		cancelButton.setDisable(false);
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
	
    
    public Label getLblStatusMessage() {
		return lblStatusMessage;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void updateStatusMessage(String statusMessage) {
    	if(Platform.isFxApplicationThread()) {
    		_updateStatusMessage(statusMessage);
    	}else {
    		Platform.runLater(() -> {
    			_updateStatusMessage(statusMessage);
    		});
    	}
    }
    
    private void _updateStatusMessage(String statusMessage) {
    	if(statusMessage == null || statusMessage.isEmpty()) {
    		lblStatusMessage.setText(JFXResourceBundle.getString("jfx.fractal.explorer.version"));
    	}else {
    		lblStatusMessage.setText(statusMessage);
    	}
    }
    
    public void updateProgress(double value) {
    	if(Platform.isFxApplicationThread()) {
    		_updaeProgress(value);
    	}else {
    		Platform.runLater(() -> {
    			_updaeProgress(value);
    		});
    	}
    }
        
    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {
    	initStage.getIcons().add(fractalIcon);
    	initStage.setTitle(JFXResourceBundle.getString("jfx.fractal.explorer.title"));
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - splashWidth / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - splashHeight / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }
    
	private void showMainStage() {
		try {

			mainStage = new Stage(StageStyle.DECORATED);
			
			colorPreference = PreferenceManager.getInstance().getColorPreference();

			colorPreferenceListner = new InvalidationListener() {
				@Override
				public void invalidated(Observable observable) {
					setBackgroundColor();
				}
			};

			colorPreference.addListener(colorPreferenceListner);

			

			BorderPane root = new BorderPane();
			root.setTop(createMenuBar());
			root.setBottom(createStatusBar());
			root.setCenter(createFractalDisplayPane());
			root.setLeft(createSettingsPane());

			Scene scene = new Scene(root, 1400, 750);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			mainStage.getIcons().add(fractalIcon);
			mainStage.setTitle(JFXResourceBundle.getString("jfx.fractal.explorer.title"));
			mainStage.setScene(scene);
			mainStage.setResizable(false);

			/*
			 * enableActionButton("draw", false); enableActionButton("animate", false);
			 */
			mainStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    private void _updaeProgress(double value) {
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
		
		MenuItem menuItemVertexOfSquare = new MenuItem("_Vertex Of Suare");
		menuItemVertexOfSquare.setMnemonicParsing(true);
		menuItemVertexOfSquare.setOnAction(new VertexOfSquareAction(this));
		menu.getItems().add(menuItemVertexOfSquare);
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
		
		MenuItem menuItemCapitalIHFractal = new MenuItem("_Captial IH Fractal");
		menuItemCapitalIHFractal.setOnAction(new CapitalIHAction(this));
		menuItemCapitalIHFractal.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemCapitalIHFractal);
		
		MenuItem menuItemCurvyTree = new MenuItem("C_urvy Tree");
		menuItemCurvyTree.setOnAction(new CurvyTreeAction(this));
		menuItemCurvyTree.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemCurvyTree);
		
		MenuItem menuItemDLA = new MenuItem("_Diffusion Limited Agregation");
		menuItemDLA.setOnAction(new DLAFractalAction(this));
		menuItemDLA.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemDLA);
		
		MenuItem menuItemFractalTree = new MenuItem("Fractal Tree");
		menuItemFractalTree.setOnAction(new FractalTreeAction(this));
		menuItemFractalTree.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemFractalTree);
		
		MenuItem menuItemGardiFractal = new MenuItem(JFXResourceBundle.getString("jfx.fractal.explorer.drawing.gardi.lblDrawing"));
		menuItemGardiFractal.setOnAction(new GardiFracalDrawingAction(this));
		menuItemGardiFractal.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemGardiFractal);
		
		MenuItem menuItemKochSnowFlake = new MenuItem("Koch Snow Flake");
		menuItemKochSnowFlake.setOnAction(new KochSnowFlakeAction(this));
		menuItemKochSnowFlake.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemKochSnowFlake);
		
		MenuItem menuItemLsystemFracal = new MenuItem("LSystem");
		menuItemLsystemFracal.setOnAction(new LSystemFractalAction(this));
		menuItemLsystemFracal.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemLsystemFracal);
		
		MenuItem menuItemPlasmaFractal = new MenuItem("Plasma Fractal");
		menuItemPlasmaFractal.setOnAction(new PlasmaFractalAction(this));
		menuItemPlasmaFractal.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemPlasmaFractal);
		
		MenuItem menuItemSnowFlakeDrawing = new MenuItem("_Snow Flake");
		menuItemSnowFlakeDrawing.setMnemonicParsing(true);
		menuItemSnowFlakeDrawing.setOnAction(new SnowFlakeDrawingAction(this));
		menu.getItems().add(menuItemSnowFlakeDrawing);
		
		MenuItem menuItemSquareTree = new MenuItem("Square Tree");
		menuItemSquareTree.setOnAction(new SquareTreeAction(this));
		menuItemSquareTree.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
		menu.getItems().add(menuItemSquareTree);
		
		MenuItem menuItemTempleFractal = new MenuItem("_Temple Fractal");
		menuItemTempleFractal.setMnemonicParsing(true);
		menuItemTempleFractal.setOnAction(new TempleFractalDrawingAction(this));
		menu.getItems().add(menuItemTempleFractal);
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
		menuItemTurtleTestDrawing.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:center;");
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
		drawButton.setOnAction(new DrawAction(this));
		toolBar.getItems().add(drawButton);
		
		animateButton = new Button(JFXResourceBundle.getString("jfx.fractal.explorer.animate.text"),getImageView("icons/animation.png"));
		animateButton.setMnemonicParsing(true);
		animateButton.setOnAction(new AnimateAction(this));
		toolBar.getItems().add(animateButton);
		
		cancelButton = new Button("Stop Rendering",getImageView("icons/cancel.png"));
		cancelButton.setMnemonicParsing(true);
		cancelButton.setOnAction(new StopRenderingAction(this));
		cancelButton.setDisable(true);
		toolBar.getItems().add(cancelButton);
		
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
		saveSettingButton.setOnAction(new SaveSettingsAction(this));
		toolBar.getItems().add(saveSettingButton);
		
		return toolBar;
	}
	
	public  ImageView getImageView(String url) {
		Image image = new Image(getClass().getResourceAsStream(url));
		
		return new ImageView(image);
	}
	
	public interface InitCompletionHandler {
        void complete();
    }
}
