package core;

import java.util.LinkedList;

import core.input.InputStrategy;

/**
 * The first filter in the KWIC system. This filter is in charge of
 * getting the input data, in whatever format it comes, and sending it
 * to all its subscribed shifters.
 * <p>
 * End of input must be marked with an empty String.
 * <p>
 * It activates all its subscribed Shifters before starting, and
 * implements threads to run concurrently to other filters.
 * @see Shifter
 */
public final class Input extends Thread {
	private LinkedList<Shifter> shifters = new LinkedList<Shifter>();
	private InputStrategy strategy;
	
	/**
	 * Initializes a new instance of the Input class, with the
	 * specified strategy it will use to get lines.
	 * @param strategy The strategy it will use.
	 */
	public Input(InputStrategy strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * Subscribes a Shifter to this Input.
	 * @param shifter Shifter to subscribe.
	 */
	public void subscribe(Shifter shifter) {
		shifters.add(shifter);
	}
	
	/**
	 * Removes a Shifter from the list of subscribers.
	 * @param Shifter to remove.
	 */
	public void unsubscribe(Shifter shifter) {
		shifters.remove(shifter);
	}
	
	/**
	 * Executes the reading process.
	 * @throws InterruptedException When the thread is interrupted
	 * while waiting for its Shifters to finish.
	 */
	public void execute() throws InterruptedException {
		for (Shifter shifter : shifters) {
			shifter.setup();
		}
		
		this.start();

		for (Shifter shifter : shifters)
			shifter.await();
	}
	
	/**
	 * The main process of input, in charge of defining the behavior
	 * of the thread it will run.
	 */
	public void run() {
		String line;
        do
        {
            line = strategy.nextLine();

            for (Shifter shifter : shifters)
                shifter.accept(line);
        } while (!line.equals(""));
	}
}
