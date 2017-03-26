package core.writer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class FileWriter implements WriteStrategy {
	private PrintWriter writer;
	
	public FileWriter(String fileName) throws FileNotFoundException {
		writer = new PrintWriter(fileName);
	}
	
	public void write(List<String> lines) {
		for (String line : lines) {
			writer.println(line);
		}
 
		writer.close();
	}
}
