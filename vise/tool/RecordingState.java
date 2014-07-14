package vise.tool;

/* When Vise is in the recording state, we have one primary 
 * job: we take gripped values and save them to our list.
 * When we close the section we save the list with the current
 * section name.
 */


public class RecordingState extends MechanicsState {
	private String sectionName = "";
	
	public RecordingState(SectionStore store) {
		super(store);
	}
	
	public void openSection(String sectionName) {
		this.sectionName = sectionName;
	}

	public void grip(Object object, String label) {
		grippedValues.add(new RecordedGrip(object, label));		
	}

	public void closeSection() {
		store.saveSection(sectionName, grippedValues);		
	}
	
	public boolean isRecording() {
		return true;
	}

}
