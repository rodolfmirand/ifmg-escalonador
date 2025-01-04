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
	public static double contextChangeCounter = 0;
	
	/* Fila com os processos */
	public static final List<Process> PROCESSES = new ArrayList<>();
	
	/* Comunicação com a view */
	protected SchedulerController schedulerController;
	
	
	public Scheduler(SchedulerController schedulerController) {
		this.schedulerController = schedulerController;
	}
	
	public abstract void run();
	
	public static double calculateAverageWaitingTime() {
		double waitingTimeSum = PROCESSES.stream()
				.mapToInt(process -> process.getWaitingTime())
				.average()
				.orElse(0.0);
		
		return Math.round(waitingTimeSum * 100.0) / 100.0;
	};
	
	public static double calculateAverageTimeSpent() {
		double turnArountTimes = PROCESSES.stream()
		        .mapToInt(process -> process.getLastExecutionTime() - process.getArrivalTime()) 
		        .average() 
		        .orElse(0.0);
	    
	    return Math.round(turnArountTimes * 100.0) / 100.0;
	}
	
	public static void resetAttributes() {
		processCounter = 0;
		executionTimeSpent = 0;
		contextChangeCounter = 0;
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
		Process process = PROCESSES.stream()
		        .filter(p -> !"Encerrado".equals(p.getStatus()))
		        .findFirst()
		        .orElse(null); 
		process.setStatus("Executando");
		
		contextChangeCounter++;
		
		return process;
	}
	
	protected synchronized void updateProcess(Process process, String status) {
		process.setStatus(status);
		PROCESSES.stream()
	        .filter(p -> p.getPid() == process.getPid()) 
	        .findFirst() 
	        .ifPresent(existingProcess -> {
	            // Atualiza o processo encontrado
	            PROCESSES.remove(existingProcess); 
	            PROCESSES.add(process); 
        });
	}
}
