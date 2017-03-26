package demo;

import java.io.FileNotFoundException;

import core.*;
import core.input.*;
import core.shifter.*;
import core.sorter.*;
import core.writer.*;

public class Demo {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException
	{
		Input input = new FileInput("test.txt");
		Shifter shifter = new StandardShifter();
		Sorter sorter = new MergeSorter();
		Writer consoleWriter = new ConsoleWriter();
		Writer fileWriter = new FileWriter("output.txt");
		
		sorter.Subscribe(consoleWriter);
		sorter.Subscribe(fileWriter);
		shifter.subscribe(sorter);
		input.subscribe(shifter);
		
		input.execute();
	}
}
