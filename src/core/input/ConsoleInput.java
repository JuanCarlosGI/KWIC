package core.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import core.Input;

public class ConsoleInput extends Input {
	
	private BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

	@Override
	protected String nextLine() {
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
