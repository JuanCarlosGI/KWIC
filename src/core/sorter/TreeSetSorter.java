package core.sorter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import core.Sorter;

public class TreeSetSorter extends Sorter {
	private TreeSet<String> set = new TreeSet<String>();
	
	@Override
	public void handleNewLine(String line) {
		set.add(line);
	}

	@Override
	public List<String> sort() {
		return new ArrayList<String>(set);
	}

}
