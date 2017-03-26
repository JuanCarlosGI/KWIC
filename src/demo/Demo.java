package demo;

import core.*;
import core.input.*;
import core.shifter.*;
import core.sorter.*;
import core.writer.*;

public class Demo {
	public static void main(String[] args) throws InterruptedException
	{
		Input input = new ConsoleInput();
		Shifter shifter = new StandardShifter();
		Sorter sorter = new MergeSorter();
		Writer writer = new ConsoleWriter();
		
		sorter.Subscribe(writer);
		shifter.subscribe(sorter);
		input.subscribe(shifter);
		
		input.execute();
	}
}
