package core.sorter;

import java.util.List;

/**
 * Interface for an object that can sort lines.
 */
public interface SortStrategy {
	/**
	 * Does processing needed to include a new line in the final
	 * sorted result.
	 * @param line The new line.
	 */
	void handleNewLine(String line);
	
	/**
	 * Returns the sorted list of lines.
	 * @return Sorted list of results.
	 */
	List<String> sort();
}
