package vise.tool;

import java.util.Iterator;

/* When Vise is in the checking state, each call to the grip method
 * retrieves the next recorded value and compares it to the value
 * sent to grip.  If the values don't match, the checking state throws
 * an exception that can be caught by JUnit, FIT or any other tool
 * that uses vise.  Grip also throws an exception if there are no
 * more values to retrieve.  The closeSection method throws an 
 * exception if there are remaining values that have not been gripped.
 * Together, these behaviors force vise to make exact matches of 
 * values it has recorded.  Any inexact match produces an exception
 * that signifies an error.
 * 
 * The logic that uses the hasThrownInCurrentSection field is there
 * to deal with some behavior that JUnit exhibits.  The most common
 * way to use Vise in JUnit is to open and close sections in setUp and
 * tearDown.  In JUnit, if an exception is thrown in code that is 
 * executed by tearDown, that exception is shown.  Any exception thrown
 * in the actual test method is discared.  This makes sense for most
 * JUnit tests.  Setup and tearDown are part of the test, not the code
 * under test, so if there is a problem in the test, it would be nice
 * to know about it before assuming that the test indicates a problem
 * with the code under test.
 * 
 *  In Vise, we want the opposite behavior.  If we thrown an exception
 *  in a test method, we know that we will not have gripped all of the 
 *  values we've recorded, so closeSection will throw an exception.  We
 *  are interested in the first error that happened: the initial mismatch.
 *  For that reason, I have logic which only throws an exception in 
 *  tearDown if we haven't thrown an exception from grip. 
 */

public class CheckingState extends MechanicsState {
	private boolean hasThrownInCurrentSection = false;
	private Iterator currentPosition;
	
	public CheckingState(SectionStore store) {
		super(store);
	}
	
	public void openSection(String sectionName) {
		grippedValues = store.retrieveSection(sectionName);
		currentPosition = grippedValues.iterator();		
	}

	public void grip(Object object, String label) {
		if(!currentPosition.hasNext()) {
			throwFromGrip("Vise can't find any more values.  All have been matched.");
			return;
		} 
		RecordedGrip grip = (RecordedGrip)currentPosition.next();
		if (!grip.value.equals(object)) {
			throwFromGrip("Vise expected grip "
					+ (grip.hasLabel() ? ("\"" + grip.label + "\" ") : "")
					+ "with <" + grip.value  + "> but was grip with <" + object + ">");
		}			
	}

	private void throwFromGrip(String message) {
		hasThrownInCurrentSection = true;
		throw new ViseException(message);
	}

	public void closeSection() {
		if (currentPosition.hasNext() && hasThrownInCurrentSection == false) {
			throw new ViseException("Vise has values with no remaining grip calls.");
		}
	}

	public boolean isRecording() {
		return false;
	}

}
