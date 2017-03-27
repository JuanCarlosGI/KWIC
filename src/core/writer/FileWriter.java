package core.writer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * FileWriter is a Concrete Write Strategy that stores all the shifted and sorted lines in a file
 */

public class FileWriter implements WriteStrategy {
	private PrintWriter writer;
	
	// Constructor that instances the writer
	public FileWriter(String fileName) throws FileNotFoundException {
		writer = new PrintWriter(fileName);
	}
	
	// Writes each line in a file
	public void write(List<String> lines) {
		for (String line : lines) {
			writer.println(line);
		}
 
		writer.close();
	}
}
