package jfx.fractal.explorer.ui.dialog;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.color.palette.ColorPalette;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;
import jfx.fractal.explorer.preference.PreferenceManager;
import jfx.fractal.explorer.resources.JFXResourceBundle;
import jfx.fractal.explorer.ui.JFXFractalExplorerDialog;

public class EditColorPreferenceDialog extends JFXFractalExplorerDialog {
	private ColorPicker penColorPicker;
	private ColorPicker fillColorPicker;
	private ColorPicker alternateColor1Picker;
	private ColorPicker alternateColor2Picker;
	private ColorPicker backgrouondColorPicker;
	private ComboBox<PenColorType> cmbPenColorType;
	private ComboBox<FillColorType> cmbFillColorType;
	private ComboBox<ColorPalette> cmbColorPalette;
	public EditColorPreferenceDialog(JFXFractalExplorer jfxFractalExplorer,String title) {
		super(jfxFractalExplorer,title);
		
	}

	@Override
	public void createContentPane() {
		ColorPreference colorPreference = PreferenceManager.getInstance().getColorPreference();
		colorPreference.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				System.out.println("ColorPreference value updated");
			}
		});
		GridPane gridPanel = new GridPane();
		gridPanel.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-alignment:center-left;-fx-padding:5");
		
		{
			Label lblPenColor = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.colorPreferece.penColor"));
			gridPanel.add(lblPenColor, 0, 0);
			
			penColorPicker = new ColorPicker(colorPreference.getPenColor());
			penColorPicker.setOnAction(e -> {
				colorPreference.setPenColor(penColorPicker.getValue());
			});
			gridPanel.add(penColorPicker, 1, 0);
		}
		
		{
			Label lblFillColor = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.colorPreferece.fillColor"));
			gridPanel.add(lblFillColor, 0, 1);
			
			fillColorPicker = new ColorPicker(colorPreference.getFillColor());
			fillColorPicker.setOnAction(e -> {
				colorPreference.setFillColor(fillColorPicker.getValue());
			});
			gridPanel.add(fillColorPicker, 1, 1);
		}
		
		{
			Label lblAlternateColor1Color = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.colorPreferece.alternateColor1"));
			gridPanel.add(lblAlternateColor1Color, 0, 2);
			
			alternateColor1Picker = new ColorPicker(colorPreference.getAlternateColor1());
			alternateColor1Picker.setOnAction(e -> {
				colorPreference.setAlternateColor1(alternateColor1Picker.getValue());
			});
			gridPanel.add(alternateColor1Picker, 1, 2);
		}
		
		{
			Label lblAlternateColor2Color = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.colorPreferece.alternateColor2"));
			gridPanel.add(lblAlternateColor2Color, 0, 3);
			
			alternateColor2Picker = new ColorPicker(colorPreference.getAlternateColor2());
			alternateColor2Picker.setOnAction(e -> {
				colorPreference.setAlternateColor2(alternateColor2Picker.getValue());
			});
			gridPanel.add(alternateColor2Picker, 1, 3);
		}
		
		{
			Label lblBackgroundColorColor = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.colorPreferece.backgroundColor"));
			gridPanel.add(lblBackgroundColorColor, 0, 4);
			
			backgrouondColorPicker = new ColorPicker(colorPreference.getBackgroundColor());
			backgrouondColorPicker.setOnAction(e -> {
				colorPreference.setBackgroundColor(backgrouondColorPicker.getValue());
			});
			gridPanel.add(backgrouondColorPicker, 1, 4);
		}
		
		{
			Label lblPenColorType = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.colorPreferece.penColorType"));
			gridPanel.add(lblPenColorType, 0, 5);
			cmbPenColorType = new ComboBox<>(FXCollections.observableArrayList(PenColorType.values()));
			cmbPenColorType.setValue(colorPreference.getPenColorType());
			cmbPenColorType.setOnAction(e->{
				colorPreference.setPenColorType(cmbPenColorType.getValue());
			});
			gridPanel.add(cmbPenColorType, 1, 5);
		}
		
		{
			Label lblFillColorType = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.colorPreferece.fillColorType"));
			gridPanel.add(lblFillColorType, 0, 6);
			cmbFillColorType = new ComboBox<>(FXCollections.observableArrayList(FillColorType.values()));
			cmbFillColorType.setValue(colorPreference.getFillColorType());
			cmbFillColorType.setOnAction(e->{
				colorPreference.setFillColorType(cmbFillColorType.getValue());
			});
			gridPanel.add(cmbFillColorType, 1, 6);
		}
		
		{
			Label lblSelectColorPalette = new Label(JFXResourceBundle.getString("jfx.fractal.explorer.colorPreferece.selectColorPalette"));
			gridPanel.add(lblSelectColorPalette, 0, 7);
			cmbColorPalette = new ComboBox<>(colorPreference.getColorPalettes());
			cmbColorPalette.setValue(colorPreference.getSelectedColorPalette());
			cmbColorPalette.setOnAction(e->{
				colorPreference.setSelectedColorPalette(cmbColorPalette.getValue());
			});
			gridPanel.add(cmbColorPalette, 1, 7);
		}
		
		getDialogPane().setContent(gridPanel);
	}

	
}
