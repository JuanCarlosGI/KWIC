package core.sorter;

import java.util.LinkedList;
import java.util.List;

import core.Sorter;

public class MergeSorter extends Sorter {
	private List<String> lines = new LinkedList<String>();
	
	@Override
	public void handleNewLine(String line) {
		lines.add(line);
	}

	@Override
	public List<String> sort() {
		lines.sort(null);
		return lines;
	}

}
