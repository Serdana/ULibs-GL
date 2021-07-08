package main.java.ulibs.gl.utils.exceptions;

public class GLException extends Exception {
	private static final long serialVersionUID = -2951449009292318484L;
	
	public GLException(Reason reason) {
		super(reason.print);
	}
	
	public enum Reason {
		failedToInitGL("Failed to initialize OpenGL!"),
		failedToInitWindow("Failed to initialize Window!"),
		notSetupVertexArray("Tried to use a non initialized vertex array!");
		
		private final String print;
		
		private Reason(String print) {
			this.print = print;
		}
	}
}
