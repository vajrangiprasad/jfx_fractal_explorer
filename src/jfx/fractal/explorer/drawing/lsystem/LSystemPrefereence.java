package jfx.fractal.explorer.drawing.lsystem;

import java.util.List;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.PenColorType;
import jfx.fractal.explorer.preference.PreferenceManager;

public class LSystemPrefereence extends FractalDrawingPreference {
	private LSystem selectedLSystem;
	private int boardSize = 450;
	private PenColorType penColorType = PenColorType.PALETTE_COLR;
	private double delay = 0.0;
	private static LSystemPrefereence instance;
	
	private  LSystemPrefereence() {
		selectedLSystem = PreferenceManager.getInstance().getLSystems().get(0);
	}
	
	public static LSystemPrefereence getInstance() {
		if(instance == null) {
			instance = new LSystemPrefereence();
		}
		
		return instance;
	}

	public LSystem getSelectedLSystem() {
		return selectedLSystem;
	}

	public void setSelectedLSystem(LSystem selectedLSystem) {
		this.selectedLSystem = selectedLSystem;
		invalidate("SelectedLSystem");
	}
	
	public String getName() {
		return selectedLSystem.getName();
	}
	
	public void setName(String name) {
		selectedLSystem.setName(name);
		invalidate("Name");
	}
	
	public String getAxiom() {
		return selectedLSystem.getAxiom();
	}
	
	public void setAxiom(String axiom) {
		selectedLSystem.setAxiom(axiom);
		invalidate("Axiom");
	}

	public double getLengthFactor() {
		return selectedLSystem.getLengthFactor();
	}

	public void setLengthFactor(double lengthFactor) {
		this.selectedLSystem.setLengthFactor(lengthFactor);
		invalidate("LenghtFactor");
	}

	public double getAngle() {
		return selectedLSystem.getAngle();
	}
	
	public void setAngle(double angle) {
		this.selectedLSystem.setAngle(angle);
		invalidate("Angle");
	}
	
	public double getLength() {
		return selectedLSystem.getLength();
	}
	
	public void setLength(double length) {
		this.selectedLSystem.setLength(length);
		invalidate("Length");
	}
	
	public int getIterations() {
		return selectedLSystem.getIterations();
	}
	
	public void setIterations(int iterations) {
		this.selectedLSystem.setIterations(iterations);
		invalidate("Iterations");
	}
	
	public double getStartX() {
		return selectedLSystem.getStartX();
	}
	
	public void setStartX(double startX) {
		this.selectedLSystem.setStartX(startX);
		invalidate("StartX");
	}
	
	public double getStartY() {
		return selectedLSystem.getStartY();
	}
	
	public void setStartY(double startY) {
		selectedLSystem.setStartY(startY);
		invalidate("StartY");
	}
	
	public List<LSystemRule> getRules() {
		return selectedLSystem.getRules();
	}

	public void addRule(LSystemRule rule) {
		selectedLSystem.getRules().add(rule);
		invalidate("AddRule");
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
		invalidate("BoardSize");
	}

	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColorType");
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}
}
