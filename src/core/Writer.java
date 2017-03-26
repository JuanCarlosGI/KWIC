package core;

import java.util.List;
import java.util.concurrent.Semaphore;

public abstract class Writer extends Thread{
	private List<String> lines;
	private Semaphore endSemaphore = new Semaphore(0, true);
	
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	protected abstract void write(List<String> lines);
	
	public void run() {
		write(lines);
		endSemaphore.release();
	}
	
	public void await() throws InterruptedException {
		endSemaphore.acquire();
	}
}
