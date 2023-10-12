package jfx.fractal.explorer.drawing.mandelbrotset;

import java.util.ArrayList;

public class MandelbrotTaskResponse {
	private int row;
	private ArrayList<Integer> iterationCounts = new ArrayList<Integer>();
	
	public MandelbrotTaskResponse(int row) {
		this.row = row;
	}
	
	public void addIterationCount(int iterationCount) {
		iterationCounts.add(iterationCount);
	}

	public int getRow() {
		return row;
	}

	public ArrayList<Integer> getIterationCounts() {
		return iterationCounts;
	}
}
