package core;

import java.util.LinkedList;

public abstract class Input extends Thread {
	private LinkedList<Shifter> Shifters = new LinkedList<Shifter>();
	
	public void subscribe(Shifter shifter) {
		Shifters.add(shifter);
	}
	
	public void unsubscribe(Shifter shifter) {
		Shifters.remove(shifter);
	}
	
	protected abstract String nextLine();
	
	public void execute() throws InterruptedException {
		for (Shifter shifter : Shifters) {
			shifter.setup();
		}
		
		this.start();
		await();
	}
	
	public void run() {
		String line;
        do
        {
            line = nextLine();

            for (Shifter shifter : Shifters)
                shifter.accept(line);
        } while (!line.equals(""));
	}
	
	public void await() throws InterruptedException {
		for (Shifter shifter : Shifters)
			shifter.await();
	}
}
