package vise.tool;

/* OutsideSectionState is the state that Vise is in prior to calling
 * openSection and after calling closeSection.  In this state, we
 * should not be able to call grip at all, since we can not be 
 * recording or checking. 
 */

public class OutsideSectionState extends MechanicsState {
	private static final String PREFIX = "invalid state error: ";

	public OutsideSectionState(SectionStore store) {
		super(store);
	}
	
	public void openSection(String sectionName) {
	}

	public void grip(Object object, String label) {
		throw new ViseException(PREFIX + "grip called without a call to openSection");
	}

	public void closeSection() {
		throw new ViseException(PREFIX + "closeSection called without a call to openSection");
	}

	public boolean isRecording() {
		return false;
	}

}
