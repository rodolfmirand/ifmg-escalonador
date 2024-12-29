package schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.Process;
import views.ProcessesTableModel;

public abstract class Scheduler implements Runnable{
	public static int processCounter = 0;
	public static int executionTimeSpent = 0;
	public static final List<Process> PROCESSES = new ArrayList<>();
	protected ProcessesTableModel processTableModel;
	
	public abstract void run();
	
	public Scheduler(ProcessesTableModel processTableModel) {
		this.processTableModel = processTableModel;
	}
	
	public void sortByArrivalTime() {
		PROCESSES.sort(Comparator.comparingInt(Process::getArrivalTime));
	}
	
	public void sortByExecutionTime() {
		PROCESSES.sort(Comparator.comparingInt(Process::getArrivalTime).thenComparingInt(Process::getSpentTime));
	}
	
	protected synchronized boolean processesIsEmpty() {
		return PROCESSES.isEmpty();
	}
	
	protected synchronized Process getProcess() {
		return PROCESSES.removeFirst();
	}
	
	protected synchronized void addProcess(Process process) {
		PROCESSES.add(process);
	}
}
