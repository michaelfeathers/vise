package vise.tool;

/* A simple exception for all Vise conditions 
 */

public class ViseException extends RuntimeException {
	public ViseException(String message) {
		super(message);
	}
}
