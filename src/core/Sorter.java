package core;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Sorter extends Thread{
	private Semaphore shifterSemaphore;
	private Semaphore queueSemaphore = new Semaphore(0, true);
	private Semaphore endSemaphore = new Semaphore(0,true);
	private Queue<String> queue = new LinkedList<String>();
	private Lock queueLock = new ReentrantLock();
	protected List<Writer> Writers = new LinkedList<Writer>();
	
	public void Subscribe(Writer writer) {
		Writers.add(writer);
	}
	
	public void Unsubscribe(Writer writer) {
		Writers.remove(writer);
	}
	
	public void setup(Semaphore semaphore) {
		shifterSemaphore = semaphore;
		this.start();
	}
	
	public abstract void handleNewLine(String line);
	
	public abstract List<String> sort();
	
	public void run() {
		while (true) {
			try {
				queueSemaphore.acquire();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
			
			queueLock.lock();
			String line = queue.poll();
			queueLock.unlock();
			
			if (line.equals("")) {
				break;
			}
			
			handleNewLine(line);
		}
		
		List<String> sorted = sort();
		for (Writer writer : Writers) {
			writer.write(sorted);
		}
		
		endSemaphore.release();
	}
	
	public void accept(String line) {
		queueLock.lock();
		queue.offer(line);
		queueSemaphore.release();
		queueLock.unlock();
	}
	
	public void end() throws InterruptedException {
		endSemaphore.acquire();
		endSemaphore.release();
		shifterSemaphore.release();
	}
}
