package core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The second filter of the KWIC System. It is in charge of finding
 * all possible rotations of a sentence and passing them to the
 * subscribed Sorters.
 * <p>
 * All its subclasses need not worry about the flow of data, and only
 * need to worry about specifying the functionality of rotating a 
 * line.
 * <p>
 * End of input must be marked with an empty String.
 * <p>
 * It activates all its subscribed Sorters before starting, and
 * implements threads to run concurrently to other filters.
 * @see Sorter
 */
public abstract class Shifter extends Thread{
	private Semaphore queueSemaphore = new Semaphore(0, true);
	private Queue<String> queue = new LinkedList<String>();
	private Lock queueLock = new ReentrantLock();
	private LinkedList<Sorter> sorters = new LinkedList<Sorter>();
	
	/**
	 * Method in charge of setting up whatever is necessary to make
	 * the rotations and know when the process is over.
	 * @param line Line to be rotated.
	 */
	protected abstract void setupRotations(String line);
	
	/**
	 * Gets the next rotation of the word that is currently being
	 * rotated.
	 * @return The next rotation, or null if it is done. Note that
	 * even when the empty string has no rotations, it will return as
	 * if it had one rotation.
	 */
	protected abstract String nextRotation();
	
	/**
	 * Adds a Sorter to the subscriber list.
	 * @param sorter Sorter to be added.
	 */
	public final void subscribe(Sorter sorter) {
		sorters.add(sorter);
	}
	
	/**
	 * Removes a Sorter from the subscribe list.
	 * @param sorter Sorter to be removed.
	 */
	public final void unsubscribe(Sorter sorter) {
		sorters.remove(sorter);
	}

	/**
	 * Starts the thread and activates all its Sorters.
	 */
	public final void setup() {
		for (Sorter sorter : sorters) {
			sorter.setup();
		}
		
		this.start();
	}
	
	/**
	 * The main process of input, in charge of defining the behavior
	 * of the thread it will run. 
	 */
	public final void run() {
	    String line;
	    do
	    {
	    	try {
				queueSemaphore.acquire();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
	    	
	        queueLock.lock();
            line = queue.poll();
            queueLock.unlock();
	
            String rotation;
            setupRotations(line);
            while ((rotation = nextRotation()) != null) {
            	for (Sorter sorter : sorters) {
    				sorter.accept(rotation);
    			}
            }
	    } while (!line.equals(""));
	}
	
	/**
	 * Waits for its work, and the work of all its subscribers, to
	 * end.
	 * @throws InterruptedException When the thread it is on is
	 * interrupted.
	 */
	public final void await() throws InterruptedException {
		for (Sorter sorter : sorters)
			sorter.await();
	}
	
	/**
	 * Accepts a new line into its queue of lines that it will shift.
	 * @param line The new line.
	 */
	public final void accept(String line) {
		queueLock.lock();
		try {
			queue.offer(line);
			queueSemaphore.release();
		} finally {
			queueLock.unlock();
		}
	}

}
