package core.writer;

import java.util.List;

/**
 * ConsoleWriter is a Concrete Write Strategy that prints all the shifted and sorted lines in console
 */

public class ConsoleWriter implements WriteStrategy {
	// Prints each line in console
	public void write(List<String> lines) {
		for (String line : lines)
			System.out.println(line);
	}
}
