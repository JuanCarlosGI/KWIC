package core.input;

/**
 * Interface for an object that can get lines from  input.
 */
public interface InputStrategy {
	/**
	 * Gets the next line in the input.
	 * @return The line of text.
	 */
	String nextLine();
}
