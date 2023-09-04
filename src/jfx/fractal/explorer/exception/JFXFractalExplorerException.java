package jfx.fractal.explorer.exception;

public class JFXFractalExplorerException extends Exception {
	private static final long serialVersionUID = 1515748051861749681L;
	
	public JFXFractalExplorerException(String errorMessage) {
		super(errorMessage);
	}
	
	public JFXFractalExplorerException(String errorMessage,Throwable t) {
		super(errorMessage,t);
	}
	
	public JFXFractalExplorerException(Throwable t) {
		super(t);
	}

}
