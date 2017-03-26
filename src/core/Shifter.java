package core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Shifter extends Thread{
	private Semaphore queueSemaphore = new Semaphore(0, true);
	private Queue<String> queue = new LinkedList<String>();
	private Lock queueLock = new ReentrantLock();
	
	protected LinkedList<Sorter> sorters = new LinkedList<Sorter>();
	
	public void subscribe(Sorter sorter) {
		sorters.add(sorter);
	}
	
	public void unsubscribe(Shifter shifter) {
		sorters.remove(shifter);
	}
	
	protected abstract void handleRotations(String line);

	public void setup() {
		for (Sorter sorter : sorters) {
			sorter.setup();
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
				System.out.println(e);
			}
	    	
	        queueLock.lock();
            line = queue.poll();
            queueLock.unlock();
	
	        handleRotations(line);
	    } while (!line.equals(""));
	}
	
	public void await() throws InterruptedException {
		for (Sorter sorter : sorters)
			sorter.await();
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
