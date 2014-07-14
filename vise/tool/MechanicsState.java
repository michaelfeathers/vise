package vise.tool;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* MechanicsState is the base for the states that Vise can be in.
 * Currently, the only common behavior for the states is inspection.
 * You can call the inspect method at any time and get a list of all 
 * of the values that the Vise has gripped in the current section.
 * This list is rendered to a string and then thrown as an exception
 * back to the caller.  While that may seem fundamentally weird, it
 * is handy.  In JUnit, all you have to do is call inspect on Vise and
 * you will have a list of all of the gripped values as an error message
 * in JUnit's display. 
 */

public abstract class MechanicsState {
	protected List grippedValues = new LinkedList();
	protected SectionStore store;

	public abstract void openSection(String sectionName);
	public abstract void grip(Object object, String label);
	public abstract void closeSection();
	public abstract boolean isRecording();
	
	public MechanicsState(SectionStore store) {
		this.store = store;
	}
	
	public void inspect() {
		int index = 0;
		HashMap labelOccurrences = new HashMap();
		class Int { public int value = 0; };

		int size = grippedValues.size();
		String message = "Vise contains " 
				+ size + " grip" + (size != 1 ? "s" : "") + "\n";
		
		for(Iterator it = grippedValues.iterator(); it.hasNext(); ) {
			RecordedGrip grip = (RecordedGrip)it.next();
			Object object = grip.value;
			message += index + ": <" + object + ">";
			if (grip.hasLabel()) {
				if (!labelOccurrences.containsKey(grip.label)) {
					labelOccurrences.put(grip.label, new Int());
				}
				Int occurrences = (Int)labelOccurrences.get(grip.label);
				occurrences.value++;
				
				message += " " + grip.label + " (" + (occurrences.value - 1) + ")"; 
			}
			message += "\n";
			index++;
		}
		throw new ViseException(message);
	}
}
