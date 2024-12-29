package models;

public class Processor {
	private static int quantum = 1;
	private static int processorsNumbers = 1;
	
	public static int getQuantum() {
		return Processor.quantum;
	}
	
	public static void setQuantum(int quantum) {
		Processor.quantum = quantum;
	}
	
	public static int getProcessorsNumbers() {
		return Processor.processorsNumbers;
	}

	public static void setProcessorsNumbers(int processorsNumbers) {
		Processor.processorsNumbers = processorsNumbers;
	}	
}
