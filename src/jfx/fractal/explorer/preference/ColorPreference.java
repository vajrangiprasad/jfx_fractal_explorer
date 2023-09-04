package jfx.fractal.explorer.preference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.color.palette.ColorPalette;

public class ColorPreference implements Observable{
	private Color penColor = Color.RED;
	private Color fillColor = Color.YELLOW;
	private Color alternateColor1 = Color.RED;
	private Color alternateColor2 = Color.YELLOW;
	private Color backgroundColor = Color.BLACK;
	private PenColorType penColorType = PenColorType.TURTLE_PEN_COLOR;
	private FillColorType fillColorType = FillColorType.PALETTE_COLR;
	private ObservableList<ColorPalette> colorPalettes = FXCollections.observableArrayList();
	private ColorPalette selectedColorPalette;
	private List<InvalidationListener> invalidationListeners = new ArrayList<InvalidationListener>();
	private static ColorPreference colorPreference;
	
	private ColorPreference() {
		loadColorPalettes();
	}
	
	public static ColorPreference getInstance() {
		if(colorPreference == null) {
			colorPreference = new ColorPreference();
		}
		
		return colorPreference;
	}
	
	public Color getPenColor() {
		return penColor;
	}
	public void setPenColor(Color penColor) {
		this.penColor = penColor;
		invalidate();
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		invalidate();
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		invalidate();
	}
	
	
	public ColorPalette getSelectedColorPalette() {
		return selectedColorPalette;
	}

	public void setSelectedColorPalette(ColorPalette selectedColorPalette) {
		this.selectedColorPalette = selectedColorPalette;
		invalidate();
	}

	
	public Color getAlternateColor1() {
		return alternateColor1;
	}

	public void setAlternateColor1(Color alternateColor1) {
		this.alternateColor1 = alternateColor1;
		invalidate();
	}

	public Color getAlternateColor2() {
		return alternateColor2;
	}

	public void setAlternateColor2(Color alternateColor2) {
		this.alternateColor2 = alternateColor2;
		invalidate();
	}

	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate();
	}

	public FillColorType getFillColorType() {
		return fillColorType;
	}

	public void setFillColorType(FillColorType fillColorType) {
		this.fillColorType = fillColorType;
		invalidate();
	}

	public ObservableList<ColorPalette> getColorPalettes() {
		return colorPalettes;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		invalidationListeners.add(listener);		
	}
	
	@Override
	public void removeListener(InvalidationListener listener) {
		invalidationListeners.remove(listener);
	}
	
	private void invalidate() {
		invalidationListeners.forEach(listener -> {
			listener.invalidated(this);
		});
	}
	
	private void loadColorPalettes() {
		List<ColorPalette> tempList ;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			byte[] jsonData = Files.readAllBytes(Paths.get("ColorPalettes.json"));
			tempList = objectMapper.readValue(jsonData, new TypeReference<List<ColorPalette>>(){});
		}catch(IOException ioe) {
			ioe.printStackTrace();
			tempList = new ArrayList<ColorPalette>();
			tempList.add(new ColorPalette("Spectrum"));
		}
		
		colorPalettes.addAll(tempList);
		setSelectedColorPalette(colorPalettes.get(0));
	}
}
