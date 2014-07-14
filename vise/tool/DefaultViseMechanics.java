package vise.tool;

/* DefaultViseMechanics manages the state transitions of Vise.  Essentially,
 * it looks for an existing section with a given name when openSection is 
 * called and if it doesn't find one, it puts Vise into the recording state.  
 * If it does find an existing section with that name, Vise goes into the 
 * checking state.
 */

public class DefaultViseMechanics implements ViseMechanics {
	private SectionStore store;
	private MechanicsState state;
	
	public DefaultViseMechanics(SectionStore store) {
		this.store = store;
		state = new OutsideSectionState(store);
	}
	
	public void openSection(String sectionName) {
		state = store.hasSection(sectionName) 
					? ((MechanicsState)new CheckingState(store)) 
				    : ((MechanicsState)new RecordingState(store));
		state.openSection(sectionName);
	}

	public boolean isRecording() {
		return state.isRecording();
	}

	public void grip(Object object) {
		state.grip(object, "");
	}
	
	public void grip(Object object, String label) {
		state.grip(object, label);
	}
	
	public void closeSection() {
		state.closeSection();
		state = new OutsideSectionState(store);
	}

	public void inspect() {
		state.inspect();
	}

	public void release() {
		store.clear();
	}

}
