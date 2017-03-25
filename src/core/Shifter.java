package core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Shifter extends Thread{

	private Semaphore endSemaphore;
	private Semaphore inputSemaphore;
	private Semaphore queueSemaphore = new Semaphore(0, true);
	private Queue<String> queue = new LinkedList<String>();
	private Lock queueLock = new ReentrantLock();
	
	protected LinkedList<Sorter> Sorters = new LinkedList<Sorter>();
	
	public void subscribe(Sorter sorter) {
		Sorters.add(sorter);
	}
	
	public void unsubscribe(Shifter shifter) {
		Sorters.remove(shifter);
	}
	
	protected abstract void handleRotations(String line);

	public void setup(Semaphore inputSemaphore) {
		endSemaphore = new Semaphore(-Sorters.size(), true);
		this.inputSemaphore = inputSemaphore;
		
		for (Sorter sorter : Sorters) {
			sorter.setup(endSemaphore);
		}
		
		this.start();
	}
	
	public void run() {
	    String line;
	    do
	    {
	    	try {
				queueSemaphore.acquire();
			} catch (InterruptedException e) {
				System.out.println("queueSemaphore for shifter was interrupted.");
			}
	    	
	        queueLock.lock();
            line = queue.poll();
            queueLock.unlock();
	
	        handleRotations(line);
	    } while (line != "");
	    
	    inputSemaphore.release();
	}
	
	public void end() throws InterruptedException {
		endSemaphore.acquire();
		endSemaphore.release();
	}
	
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
