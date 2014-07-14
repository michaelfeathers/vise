package vise.tests;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import vise.tool.FileBasedStore;

public class FileBasedStoreTest extends TestCase {
	public static final String DIRECTORY_BASE = "./persistedvisefiles";
	private FileBasedStore store;
	
	protected void setUp() {
		store = new FileBasedStore(DIRECTORY_BASE);
	}
	
	protected void tearDown() {
		store.clear();
	}
	
	public void testHasSection() {
		assertFalse(store.hasSection("example"));
	}
	
	public void testSaveSection() {
		List values = new LinkedList();
		values.add(new Integer(3));
		store.saveSection("anExample", values);
		assertTrue(store.hasSection("anExample"));
		
		List retrievedValues = store.retrieveSection("anExample");
		assertEquals(new Integer(3), retrievedValues.iterator().next());
	}
	
	public void testClear() {
		final String SECTION_NAME = "alpha";
		List values = new LinkedList();
		values.add(new Integer(3));
		store.saveSection(SECTION_NAME, values);
		store.clear();
		assertFalse(store.hasSection(SECTION_NAME));
	}

}
