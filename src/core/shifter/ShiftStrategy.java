package core.shifter;

/**
 * Interface for an object that can shift lines.
 */
public interface ShiftStrategy {
	/**
	 * Method in charge of setting up whatever is necessary to make
	 * the rotations and know when the process is over.
	 * @param line Line to be rotated.
	 */
	void setupRotations(String line);
	
	/**
	 * Gets the next rotation of the word that is currently being
	 * rotated.
	 * @return The next rotation, or null if it is done. Note that
	 * even when the empty string has no rotations, it will return as
	 * if it had one rotation.
	 */
	String nextRotation();
}
