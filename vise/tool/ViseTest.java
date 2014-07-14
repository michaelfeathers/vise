package vise.tool;

import junit.framework.TestCase;

/* ViseTest is a convenient superclass for JUnit tests that use Vise. 
 * 
 * To use Vise, all you have to do is inherit from ViseTest and make
 * grip calls in code called by your test methods.  Vise takes care
 * of everything else.
 * 
 * By default, sections are named using the name of the the test case
 * class and the name of the test method.  The rule is that these names
 * have to be unique.
 */

public abstract class ViseTest extends TestCase {
		protected void setUp() {
			Vise.openSection(getClass().getName() + "." + getName());
		}
		
		protected void tearDown() {
			Vise.closeSection();
		}
}
