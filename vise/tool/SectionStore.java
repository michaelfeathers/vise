package vise.tool;
import java.util.List;

/* SectionStore helps us store and retrieve the values
 * that we grip in a section.  The primary implementor
 * of SectionStore for Vise is FileBasedSectionStore.  In
 * testing, we can do our work in memory using
 * FakeSectionStore.
 */

public interface SectionStore {
	public boolean hasSection(String sectionName);
	public void saveSection(String sectionName, List values);
	public List retrieveSection(String sectionName);
	public int size(String sectionName);
	public void clear();
}