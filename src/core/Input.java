package core;

import java.util.LinkedList;

public abstract class Input extends Thread {
	private LinkedList<Shifter> shifters = new LinkedList<Shifter>();
	
	public void subscribe(Shifter shifter) {
		shifters.add(shifter);
	}
	
	public void unsubscribe(Shifter shifter) {
		shifters.remove(shifter);
	}
	
	protected abstract String nextLine();
	
	public void execute() throws InterruptedException {
		for (Shifter shifter : shifters) {
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

            for (Shifter shifter : shifters)
                shifter.accept(line);
        } while (!line.equals(""));
	}
	
	public void await() throws InterruptedException {
		for (Shifter shifter : shifters)
			shifter.await();
	}
}
