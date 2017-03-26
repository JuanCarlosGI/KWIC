package core;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * The final filter of the KWIC System. It is in charge of printing
 * a set of lines in a parallel manner.
 */
public abstract class Writer extends Thread{
	private List<String> lines;
	private Semaphore endSemaphore = new Semaphore(0, true);
	
	/**
	 * Sets the list of lines it will have to print.
	 * @param lines
	 */
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	/**
	 * Method in charge of printing a set of lines.
	 * @param lines Lines to be printed.
	 */
	protected abstract void write(List<String> lines);
	
	/**
	 * Prints the list of lines that has been set.
	 */
	public void run() {
		write(lines);
		endSemaphore.release();
	}
	
	/**
	 * Waits for the writer to end.
	 * @throws InterruptedException When the thread it is waiting on
	 * is interrupted.
	 */
	public void await() throws InterruptedException {
		endSemaphore.acquire();
		endSemaphore.release();
	}
}
