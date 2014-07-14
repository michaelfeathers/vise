package vise.tests;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vise.tool.SectionStore;


public class FakeSectionStore implements SectionStore {
	private Map sections = new HashMap();
	
	public boolean hasSection(String sectionName) {
		return sections.containsKey(sectionName);
	}

	public void saveSection(String sectionName, List values) {
		sections.put(sectionName, values);
	}
	
	public int size(String sectionName) {
		List section = (List)sections.get(sectionName);
		return section != null ? section.size() : 0;
	}

	public List retrieveSection(String sectionName) {
		return (List)sections.get(sectionName);
	}

	public void clear() {
		sections = new HashMap();
	}
}
