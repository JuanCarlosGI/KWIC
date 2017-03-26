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
		
		Sorter treeSorter = new TreeSetSorter();
		Writer treeConsoleWriter = new ConsoleWriter();
		Writer treeFileWriter = new FileWriter("treeOutput.txt");
		treeSorter.Subscribe(treeConsoleWriter);
		treeSorter.Subscribe(treeFileWriter);
		
		Sorter mergeSorter = new MergeSorter();
		Writer mergeConsoleWriter = new ConsoleWriter();
		Writer mergeFileWriter = new FileWriter("mergeOutput.txt");
		mergeSorter.Subscribe(mergeConsoleWriter);
		mergeSorter.Subscribe(mergeFileWriter);
		
		shifter.subscribe(treeSorter);
		shifter.subscribe(mergeSorter);
		input.subscribe(shifter);
		
		input.execute();
	}
}
