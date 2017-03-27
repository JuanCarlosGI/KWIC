package core.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * FileInput is a Concrete Strategy that read the next line in the buffer loaded from a file.
 * Returns the lower cased line obtained.
 */

public class FileInput implements InputStrategy {
	private BufferedReader reader;
	
	public FileInput(String fileName) throws FileNotFoundException {
		 reader = new BufferedReader(new FileReader(fileName));
	}
	
	public String nextLine() {
		String line;
		try {
			line = reader.readLine();

			if (line == null) {
				line = "";
			}
			
			line = line.toLowerCase();
			if (line.length() > 0 && line.toCharArray()[line.length()-1] == '.')
			{
				line = line.substring(0, line.length() - 1);
			}
			
			return line;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
