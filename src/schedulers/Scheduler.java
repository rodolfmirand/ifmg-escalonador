package schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import controller.SchedulerController;
import models.Process;
import views.ProcessesTableModel;

public abstract class Scheduler implements Runnable{
	public static int processCounter = 0;
	public static double executionTimeSpent = 0;
	public static final List<Process> PROCESSES = new ArrayList<>();
	protected SchedulerController schedulerController;
	
	public abstract void run();
	public abstract double calculateWaitingTime();
	
	public static double calculateAverageTimeSpent() {
		return executionTimeSpent/processCounter;
	}
	
	public Scheduler(SchedulerController schedulerController) {
		this.schedulerController = schedulerController;
	}
	
	public void sortByArrivalTime() {
		PROCESSES.sort(Comparator.comparingInt(Process::getArrivalTime));
	}
	
	public void sortByExecutionTime() {
		PROCESSES.sort(Comparator.comparingInt(Process::getArrivalTime).thenComparingInt(Process::getSpentTime));
	}
	
	/* Operações sobre a lista de processos */
	protected static synchronized void setExecutionTimeSpent(int processExecutionTimeSpent) {
		executionTimeSpent += processExecutionTimeSpent;
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
