package core.sorter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * TreeSetSorter is a Concrete Sorter Strategy that sorts the lines in descending order
 */

public class TreeSetSorter implements SortStrategy {
	private TreeSet<String> set = new TreeSet<String>();
	
	// Adds the line to the TreeSet
	public void handleNewLine(String line) {
		set.add(line);
	}
	
	// Returns the automatically sorted TreeSet
	public List<String> sort() {
		return new ArrayList<String>(set);
	}

}
