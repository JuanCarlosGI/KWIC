package core.writer;

import java.util.List;

/**
 * Interface for an object that can write a list of lines.
 */
public interface WriteStrategy {
	/**
	 * Method in charge of printing a set of lines.
	 * @param lines Lines to be printed.
	 */
	void write(List<String> lines);
}
