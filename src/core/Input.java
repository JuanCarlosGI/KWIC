package core;

import java.util.LinkedList;

/**
 * The first filter in the KWIC system. This filter is in charge of
 * getting the input data, in whatever format it comes, and sending it
 * to all its subscribed shifters.
 * <p>
 * All its subclasses need not worry about the flow of data, and only
 * need to worry about specifying the functionality of getting a line.
 * <p>
 * End of input must be marked with an empty String.
 * <p>
 * It activates all its subscribed Shifters before starting, and
 * implements threads to run concurrently to other filters.
 * @see Shifter
 */
public abstract class Input extends Thread {
	private LinkedList<Shifter> shifters = new LinkedList<Shifter>();
	private void await() throws InterruptedException {
		for (Shifter shifter : shifters)
			shifter.await();
	}
	
	/**
	 * Gets the next line in the input.
	 * @return The line of text.
	 */
	protected abstract String nextLine();
	
	/**
	 * Subscribes a Shifter to this Input.
	 * @param shifter Shifter to subscribe.
	 */
	public final void subscribe(Shifter shifter) {
		shifters.add(shifter);
	}
	
	/**
	 * Removes a Shifter from the list of subscribers.
	 * @param Shifter to remove.
	 */
	public final void unsubscribe(Shifter shifter) {
		shifters.remove(shifter);
	}
	
	/**
	 * Executes the reading process.
	 * @throws InterruptedException When the thread is interrupted
	 * while waiting for its Shifters to finish.
	 */
	public final void execute() throws InterruptedException {
		for (Shifter shifter : shifters) {
			shifter.setup();
		}
		
		this.start();
		await();
	}
	
	/**
	 * The main process of input, in charge of defining the behavior
	 * of the thread it will run.
	 */
	public final void run() {
		String line;
        do
        {
            line = nextLine();

            for (Shifter shifter : shifters)
                shifter.accept(line);
        } while (!line.equals(""));
	}
}
