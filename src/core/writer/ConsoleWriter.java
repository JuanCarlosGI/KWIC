package core.writer;

import java.util.List;

public class ConsoleWriter implements WriteStrategy {
	public void write(List<String> lines) {
		for (String line : lines)
			System.out.println(line);
	}
}
