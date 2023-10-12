package jfx.fractal.explorer.drawing.mandelbrotset;

import java.util.concurrent.Callable;

import jfx.fractal.explorer.util.ComplexNumber;

public class MandelbrotTask implements Callable<MandelbrotTaskResponse>{
	private final double topLeftX,bottomRightX, yval;
	private final int columnCount;
	private final int rowNumber;
	private final int maxIterations;
	private boolean isJuliaSet = false;
	private ComplexNumber cj ;
	
	public MandelbrotTask(double topLeftX,
			double bottomRightX,
			double yval,
			int columCount,
			int rowNumber,
			int maxIterations) {
		this.topLeftX = topLeftX;
		this.bottomRightX = bottomRightX;
		this.yval = yval;
		this.columnCount = columCount;
		this.rowNumber = rowNumber;
		this.maxIterations = maxIterations;
	}
	
	public boolean isJuliaSet() {
		return isJuliaSet;
	}


	public void setJuliaSet(boolean isJuliaSet) {
		this.isJuliaSet = isJuliaSet;
	}

	public ComplexNumber getCj() {
		return cj;
	}

	public void setCj(ComplexNumber cj) {
		this.cj = cj;
	}

	@Override
	public MandelbrotTaskResponse call() throws Exception {
		MandelbrotTaskResponse response = new MandelbrotTaskResponse(rowNumber);
		
		double dx = Math.abs((bottomRightX-topLeftX)/(columnCount-1.0));
		
		for (int i = 0; i <= columnCount; i++) {
			if(isJuliaSet) {
				response.addIterationCount(countIterations(cj,new ComplexNumber(topLeftX+(dx*i),yval)));
			}else {
				response.addIterationCount(countIterations(new ComplexNumber(topLeftX+(dx*i),yval),new ComplexNumber(0,0)));
			}
		}
		
		return response;
	}
	
	private int countIterations(ComplexNumber c,ComplexNumber z) {
		for(int t = 0; t < maxIterations;t++) {
			if(z.abs() > 2) {
				return t;
			}
			z = z.times(z).plus(c);
		}
		
		return maxIterations;
	}

}
