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
		Input input = new Input(new FileInput("test.txt"));
		Shifter shifter = new Shifter(new StandardShifter());
		
		Sorter treeSorter = new Sorter(new TreeSetSorter());
		Writer treeConsoleWriter = new Writer(new ConsoleWriter());
		Writer treeFileWriter = new Writer(new FileWriter("treeOutput.txt"));
		treeSorter.subscribe(treeConsoleWriter);
		treeSorter.subscribe(treeFileWriter);
		
		Sorter mergeSorter = new Sorter(new MergeSorter());
		Writer mergeConsoleWriter = new Writer(new ConsoleWriter());
		Writer mergeFileWriter = new Writer(new FileWriter("mergeOutput.txt"));
		mergeSorter.subscribe(mergeConsoleWriter);
		mergeSorter.subscribe(mergeFileWriter);
		
		shifter.subscribe(treeSorter);
		shifter.subscribe(mergeSorter);
		input.subscribe(shifter);
		
		input.execute();
	}
}
