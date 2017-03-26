package core.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput implements InputStrategy {
	
	private BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

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
