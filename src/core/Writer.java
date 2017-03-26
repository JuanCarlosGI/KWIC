package core;

import java.util.List;
import java.util.concurrent.Semaphore;

import core.writer.WriteStrategy;

/**
 * The final filter of the KWIC System. It is in charge of printing
 * a set of lines in a parallel manner.
 */
public final class Writer extends Thread{
	private List<String> lines;
	private Semaphore endSemaphore = new Semaphore(0, true);
	private WriteStrategy strategy;
	
	/**
	 * Initializes a new instance of the Writer class, with the
	 * specified strategy it will use to write lines.
	 * @param strategy The strategy it will use.
	 */
	public Writer(WriteStrategy strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * Sets the list of lines it will have to print.
	 * @param lines
	 */
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	/**
	 * Prints the list of lines that has been set.
	 */
	public void run() {
		strategy.write(lines);
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
