package vise.tool;

import java.io.Serializable;

/* RecordedGrip is a handy little class that associates a recorded
 * grip value with any label that a Vise user supplies.
 * 
 * Vise files are a list of RecordedGrip objects.
 */


public class RecordedGrip implements Serializable {
	public Object value;
	public String label;
	
	public RecordedGrip(Object value) {
		this(value,"");
	}
	
	public RecordedGrip(Object value, String label) {
		this.value = value;
		this.label = label;
	}

	public boolean hasLabel() {
		return label.length() != 0;
	}
}
