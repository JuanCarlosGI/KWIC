package core;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Sorter extends Thread{
	private Semaphore queueSemaphore = new Semaphore(0, true);
	private Queue<String> queue = new LinkedList<String>();
	private Lock queueLock = new ReentrantLock();
	protected List<Writer> writers = new LinkedList<Writer>();
	
	public void Subscribe(Writer writer) {
		writers.add(writer);
	}
	
	public void Unsubscribe(Writer writer) {
		writers.remove(writer);
	}
	
	public void setup() {
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
		for (Writer writer : writers) {
			writer.setLines(sorted);
			writer.start();
		}
	}
	
	public void accept(String line) {
		queueLock.lock();
		queue.offer(line);
		queueSemaphore.release();
		queueLock.unlock();
	}
	
	public void await() throws InterruptedException {
		for (Writer writer : writers) {
			writer.await();
		}
	}
}
