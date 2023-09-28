package jfx.fractal.explorer.preference;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfx.fractal.explorer.drawing.lsystem.LSystem;

public class PreferenceManager {
	private static PreferenceManager preferenceManager;
	private ColorPreference colorPreference;
	private TurtlePreference turtlePreference;
	private ObservableList<LSystem> lsystems ;
	
	private PreferenceManager() {
		colorPreference =ColorPreference.getInstance();
		turtlePreference = TurtlePreference.getInstance();
		loadLSystems();
	}
	
	public static PreferenceManager getInstance() {
		if(preferenceManager == null) {
			preferenceManager = new PreferenceManager();
		}
		
		return preferenceManager;
	}
	
	public ColorPreference getColorPreference() {
		return colorPreference;
	}

	public TurtlePreference getTurtlePreference() {
		return turtlePreference;
	}
	
	public ObservableList<LSystem> getLSystems() {
		return lsystems;
	}
	
	public void addLSystem(LSystem lSystem) {
		lsystems.add(lSystem);
	}
	
	public void removeLSystem(LSystem lSystem) {
		lsystems.remove(lSystem);
	}
	private void loadLSystems() {
		ArrayList<LSystem> lSystemList = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			byte[] jsonData;
			jsonData = Files.readAllBytes(Paths.get("LSystems.json"));
			lSystemList = objectMapper.readValue(jsonData, new TypeReference<ArrayList<LSystem>>(){});
		}catch(Exception ex) {
			lSystemList = new ArrayList<LSystem>();
		}
		
		lsystems = FXCollections.observableArrayList(lSystemList);
		lsystems.sort(new Comparator<LSystem>() {
			@Override
			public int compare(LSystem o1, LSystem o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}
}
