package core.sorter;

import java.util.LinkedList;
import java.util.List;

public class MergeSorter implements SortStrategy {
	private List<String> lines = new LinkedList<String>();
	
	public void handleNewLine(String line) {
		lines.add(line);
	}

	public List<String> sort() {
		lines.sort(null);
		return lines;
	}

}
