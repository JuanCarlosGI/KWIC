package core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import core.shifter.ShiftStrategy;

/**
 * The second filter of the KWIC System. It is in charge of finding
 * all possible rotations of a sentence and passing them to the
 * subscribed Sorters.
 * <p>
 * End of input must be marked with an empty String.
 * <p>
 * It activates all its subscribed Sorters before starting, and
 * implements threads to run concurrently to other filters.
 * @see Sorter
 */
public final class Shifter extends Thread{
	private Semaphore queueSemaphore = new Semaphore(0, true);
	private Queue<String> queue = new LinkedList<String>();
	private Lock queueLock = new ReentrantLock();
	private LinkedList<Sorter> sorters = new LinkedList<Sorter>();
	private ShiftStrategy strategy;
	
	/**
	 * Initializes a new instance of the Shifter class, with the
	 * specified strategy it will use to shift lines.
	 * @param strategy The strategy it will use.
	 */
	public Shifter(ShiftStrategy strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * Adds a Sorter to the subscriber list.
	 * @param sorter Sorter to be added.
	 */
	public void subscribe(Sorter sorter) {
		sorters.add(sorter);
	}
	
	/**
	 * Removes a Sorter from the subscribe list.
	 * @param sorter Sorter to be removed.
	 */
	public void unsubscribe(Sorter sorter) {
		sorters.remove(sorter);
	}

	/**
	 * Starts the thread and activates all its Sorters.
	 */
	public void setup() {
		for (Sorter sorter : sorters) {
			sorter.setup();
		}
		
		this.start();
	}
	
	/**
	 * The main process of input, in charge of defining the behavior
	 * of the thread it will run. 
	 */
	public void run() {
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
            strategy.setupRotations(line);
            while ((rotation = strategy.nextRotation()) != null) {
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
	public void await() throws InterruptedException {
		for (Sorter sorter : sorters)
			sorter.await();
	}
	
	/**
	 * Accepts a new line into its queue of lines that it will shift.
	 * @param line The new line.
	 */
	public void accept(String line) {
		queueLock.lock();
		try {
			queue.offer(line);
			queueSemaphore.release();
		} finally {
			queueLock.unlock();
		}
	}

}
