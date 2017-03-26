package core.sorter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class TreeSetSorter implements SortStrategy {
	private TreeSet<String> set = new TreeSet<String>();
	
	public void handleNewLine(String line) {
		set.add(line);
	}

	public List<String> sort() {
		return new ArrayList<String>(set);
	}

}
