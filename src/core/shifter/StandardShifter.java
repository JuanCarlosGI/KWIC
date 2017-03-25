package core.shifter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import core.Shifter;
import core.Sorter;

public class StandardShifter extends Shifter{
	
	@Override
	protected void handleRotations(String line) {
		for (Sorter sorter : Sorters) {
			sorter.accept(line);
		}
		
		List<String> fragments = new LinkedList<String>(Arrays.asList(line.split(" ")));
		for (int i = 1; i < fragments.size(); i++) {
			String aux = fragments.remove(0);
			fragments.add(aux);
			
			String newLine = String.join(" ", fragments);
			for (Sorter sorter : Sorters) {
				sorter.accept(newLine);
			}
		}
	}

}
