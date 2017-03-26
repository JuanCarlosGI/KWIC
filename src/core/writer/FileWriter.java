package core.writer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import core.Writer;

public class FileWriter extends Writer {
	private PrintWriter writer;
	
	public FileWriter(String fileName) throws FileNotFoundException {
		writer = new PrintWriter(fileName);
	}
	
	@Override
	protected void write(List<String> lines) {
		for (String line : lines) {
			writer.println(line);
		}
 
		writer.close();
	}
}
