package core.writer;

import java.util.List;

import core.Writer;

public class ConsoleWriter extends Writer{

	@Override
	public void write(List<String> lines) {
		for (String line : lines)
			System.out.println(line);
	}

}
