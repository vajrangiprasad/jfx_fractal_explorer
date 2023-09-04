package jfx.fractal.explorer;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfx.fractal.explorer.actions.EditColorPreferenceAction;
import jfx.fractal.explorer.actions.ExitAction;
import jfx.fractal.explorer.actions.HelpAboutAction;
import jfx.fractal.explorer.resources.JFXResourceBundle;


public class JFXFractalExplorer extends Application {
	private Stage mainStage;
	private Image fractalIcon;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.mainStage = primaryStage;
						
			BorderPane root = new BorderPane();
			root.setTop(createMenuBar());
			/*root.setBottom(createStatusBar());
			createTurtleScreen();
			root.setCenter(centerPane);
			root.setLeft(createSettingsPane());*/
			
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
}
