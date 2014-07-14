package vise.tool;

/*   Vise Mechanics defines the fundamental abstraction of Vise.  It allows users
 *   to open and close sections, it defines an inspect method which lets users see 
 *   the current contents of the vise, and it defines a release method which 
 *   releases all griped values and essentially puts vise back into the recording state. 
 */

public interface ViseMechanics {
	void openSection(String sectionName);
	void closeSection();
	boolean isRecording();
	void grip(Object object);
	void grip(Object object, String label);
	void inspect();
	void release();
}
