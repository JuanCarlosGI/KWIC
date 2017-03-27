package core.shifter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * StandardShifter is a Concrete Strategy that is in charge of shift each given line
 */

public class StandardShifter implements ShiftStrategy {
	private List<String> fragments; // List of tokens(words) of a line
	private int shiftCounter; // Number of shifts made
	private int size = 0; // Number of tokens
	
	// Get the tokens, initialize its shift counter, get the number of tokens
	public void setupRotations(String line) {
		fragments = new LinkedList<String>(Arrays.asList(line.split(" ")));
		shiftCounter = 0;
		size = fragments.size();
	}
	
	// Shifts the tokens in the list and returns them joined as a string
	public String nextRotation() {
		if (size == 0 && shiftCounter == 0) {
			shiftCounter++;
			return "";	
		}
		if (shiftCounter < size) {
			String aux = fragments.remove(0);
			fragments.add(aux);	
			shiftCounter++;
			
			return String.join(" ", fragments);
		}
		
		return null;
	}

}
