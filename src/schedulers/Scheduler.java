package schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import controller.SchedulerController;
import models.Process;
import views.components.ProcessesTableModel;

public abstract class Scheduler implements Runnable{
	/* Variáveis de controle do Scheduler */
	public static int processCounter = 0;
	public static double executionTimeSpent = 0;
	public static int contextChangeCounter = 0;
	
	/* Fila com os processos */
	public static final List<Process> PROCESSES = new ArrayList<>();
	
	/* Comunicação com a view */
	protected SchedulerController schedulerController;
	
	public abstract void run();
	
	public static double calculateAverageWaitingTime() {
		int waitingTimeSum =  PROCESSES.stream().mapToInt(Process::getWaitingTime).sum();
		
		return PROCESSES.size() > 0 ? waitingTimeSum/PROCESSES.size() : 0;
	};
	
	public static double calculateAverageTimeSpent() {
		return executionTimeSpent/processCounter;
	}
	
	public Scheduler(SchedulerController schedulerController) {
		this.schedulerController = schedulerController;
	}
	
	public static void sortByArrivalTime() {
		PROCESSES.sort(Comparator.comparingInt(Process::getArrivalTime));
	}
	
	public static void sortByExecutionTime() {
		PROCESSES.sort(Comparator.comparingInt(Process::getArrivalTime).thenComparingInt(Process::getSpentTime));
	}
	
	/* Operações sobre a lista de processos */
	protected static synchronized void setExecutionTimeSpent(int processExecutionTimeSpent) {
		executionTimeSpent += processExecutionTimeSpent;
	}
	
	protected synchronized boolean processesIsEmpty() {
		return PROCESSES.stream().allMatch(process -> "Encerrado".equals(process.getStatus()));
	}
	
	protected synchronized Process getProcess() {
		contextChangeCounter++;
		Process process = PROCESSES.removeFirst();
		process.setStatus("Executando");
		
		return process;
	}
	
	protected synchronized void addProcess(Process process, String status) {
		process.setStatus(status);
		PROCESSES.add(process);
	}
}
