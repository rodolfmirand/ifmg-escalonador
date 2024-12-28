package models;

public class Quantum {
	private static int quantum = 2;

	public static int getValue() {
		return quantum;
	}

	public static void setValue(int quantum) {
		Quantum.quantum = quantum;
	}	
}
