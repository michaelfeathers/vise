package vise.tests;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import vise.tool.DefaultViseMechanics;
import vise.tool.RecordedGrip;
import vise.tool.SectionStore;
import vise.tool.ViseException;
import vise.tool.ViseMechanics;

public class DefaultViseMechanicsTest extends TestCase {
	private SectionStore store;
	private ViseMechanics vise;
	
	protected void setUp() {
		store = new FakeSectionStore();
		vise = new DefaultViseMechanics(store);		
	}
	
	public void testSaveSection() {
		vise.openSection("aSection");
		assertTrue(vise.isRecording());
	}
	
	public void testGrip() {
		vise.openSection("aSection");
		vise.grip(new Integer(3));
		vise.closeSection();
		assertEquals(1, store.size("aSection"));
	}
	
	public void testSwitchOffRecording() {
		vise.openSection("aSection");
		assertTrue(vise.isRecording());
		
		List variables = new LinkedList();
		store.saveSection("anotherSection", variables);
		vise.openSection("anotherSection");
		assertFalse(vise.isRecording());
	}
	
	public void testGripPlaybackFailed() {
		List variables = new LinkedList();
		variables.add(new RecordedGrip(new Integer(2)));		
		store.saveSection("aSection", variables);
		vise.openSection("aSection");
		try {
			vise.grip(new Integer(3));
			fail("expected vise exception");
		} catch (ViseException e) {
			assertEquals("Vise expected grip with <2> but was grip with <3>", e.getMessage());
		}
		vise.closeSection();
	}
	
	public void testGripPlaybackErrorTooFew() {
		List variables = new LinkedList();
		store.saveSection("aSection", variables);
		vise.openSection("aSection");
		try {
			vise.grip(new Integer(3));
			fail("expected vise exception");
		} catch (ViseException e) {
			assertEquals("Vise can't find any more values.  All have been matched.", e.getMessage());
		}
		vise.closeSection();
	}		
	
	public void testGripPlaybackErrorTooMany() {
		List variables = new LinkedList();
		variables.add(new Integer(3));
		store.saveSection("aSection", variables);
		vise.openSection("aSection");
		try {
			vise.closeSection();
			fail("expected vise exception");
		} catch(ViseException e) {
			assertEquals("Vise has values with no remaining grip calls.", e.getMessage());
		}
	}		
	
	public void testMultiple() {
		// Make a preexisting aSection
		List variables = new LinkedList();
		variables.add(new RecordedGrip("a"));
		store.saveSection("aSection", variables);
		
		// Open the existing section
		vise.openSection("aSection");
		vise.grip("a");
		vise.closeSection();
		
		// Open a nonexisting section for recording
		vise.openSection("bSection");
		vise.closeSection();
		
		// Open for retrieval an see that the things from aSection aren't there
		vise.openSection("bSection");
		vise.closeSection();		
	}
	
	public void testInspectSingle() {
		vise.openSection("aSection");
		vise.grip("a");
		try {
			vise.inspect();
			fail("expected vise exception");
		} catch(ViseException e) {
			assertEquals("Vise contains 1 grip\n0: <a>\n", e.getMessage());
		} finally {
			vise.closeSection();
		}
	}
	
	public void testInspectMultiple() {
		vise.openSection("aSection");
		vise.grip("a");
		vise.grip("b");
		try {
			vise.inspect();
			fail("expected vise exception");
		} catch(ViseException e) {
			assertEquals("Vise contains 2 grips\n0: <a>\n1: <b>\n", e.getMessage());
		} finally {
			vise.closeSection();
		}
	}	
	
	public void testInspectNone() {
		vise.openSection("aSection");
		try {
			vise.inspect();
			fail("expected vise exception");
		} catch(ViseException e) {
			assertEquals("Vise contains 0 grips\n", e.getMessage());
		} finally {
			vise.closeSection();
		}
	}	
	
	public void testNoThrowFromCloseIfAlreadyThrown() {
		// record a section
		vise.openSection("aSection");
		vise.grip("a");
		vise.grip("b");
		vise.closeSection();
		
		// grip a failure but leave one remaining
		vise.openSection("aSection");
		try {
			vise.grip("c");
			fail("expected vise exception");
		} catch(ViseException e) {
		}
		
		// close section without an exception
		vise.closeSection();
	}
	
	public void testReleaseVise() {
		//	 record a section
		vise.openSection("aSection");
		vise.grip("a");
		vise.grip("b");
		vise.closeSection();
		
		vise.release();
		
		vise.openSection("aSection");
		assertTrue(vise.isRecording());
		vise.closeSection();
	}
	
	public void testGripWithNoOpen() {
		try {
			vise.grip("a");
			fail("expected vise exception");
		} catch(ViseException e) {
			assertEquals("invalid state error: grip called without a call to openSection", e.getMessage());
		}
	}
	
	public void testCloseWithNoOpen() {
		try {
			vise.closeSection();
			fail("expected vise exception");
		} catch(ViseException e) {
			assertEquals("invalid state error: closeSection called without a call to openSection", e.getMessage());
		}
	}	
	
	public void testResetToOutsideSectionOnClose() {
		try {
			vise.openSection("a");
			vise.closeSection();
			vise.grip("a");
			fail("expected vise exception");
		} catch(ViseException e) {
			assertEquals("invalid state error: grip called without a call to openSection", e.getMessage());
		}
	}		
	
	public void testGripWithLabel() {
		vise.openSection("a");
		vise.grip("1", "inner loop value");
		vise.closeSection();
		vise.openSection("a");
		try {
			vise.inspect();
		} catch(ViseException e) {
			assertEquals("Vise contains 1 grip\n0: <1> inner loop value (0)\n", e.getMessage());
		}
		vise.grip("1");
		vise.closeSection();
	}
	
	public void testGripWithAdvancingLabel() {
		vise.openSection("a");
		vise.grip("1", "inner loop value");
		vise.grip("1", "inner loop value");
		vise.closeSection();
		vise.openSection("a");
		try {
			vise.inspect();
		} catch(ViseException e) {
			assertEquals("Vise contains 2 grips\n" 
					+ "0: <1> inner loop value (0)\n"
					+ "1: <1> inner loop value (1)\n",
					e.getMessage());
		}
		vise.grip("1");
		vise.grip("1");
		vise.closeSection();
	}
	
	public void testFailureOfGripWithLabel() {
		vise.openSection("a");
		vise.grip("1", "inner loop value");
		vise.closeSection();
		vise.openSection("a");
		try {
			vise.grip("2");
		} catch(ViseException e) {
			assertEquals("Vise expected grip \"inner loop value\" with <1> but was grip with <2>", e.getMessage()); 
		} finally {
			vise.closeSection();
		}
		
	}
}
