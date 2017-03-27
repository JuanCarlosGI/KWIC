package core.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Console Input is a Concrete Strategy that read the next line in the buffer from console and lower case it.
 * Returns the lower cased line obtained.
 */
public class ConsoleInput implements InputStrategy {
	
	private BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	
	// Gets the next line of the list of lines given in the console and returns it
	public String nextLine() {
		try {
			String line = buffer.readLine();
			line = line.toLowerCase();
			if (line.length() > 0 && line.toCharArray()[line.length()-1] == '.')
			{
				line = line.substring(0, line.length() - 1);
			}
			return line;
			
		} catch (IOException e) {
			System.out.println(e);
			return "";
		}
	}

}
