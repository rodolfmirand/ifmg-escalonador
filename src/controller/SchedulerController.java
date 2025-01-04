package controller;

import java.util.ArrayList;
import java.util.HashMap;

import factories.SchedulerFactory;
import models.Process;
import models.Processor;
import schedulers.Scheduler;
import views.components.ProcessesTableModel;

public class SchedulerController {
	private final ProcessesTableModel processesTableModel;
	
	public SchedulerController(ProcessesTableModel processesTableModel) {
		this.processesTableModel = processesTableModel;
	}
	
	public void addProcess(int arrivalTime, int spentTime) {
	    Process process = new Process(++Scheduler.processCounter, arrivalTime, spentTime);
	    Scheduler.PROCESSES.add(process); 
	    this.processesTableModel.addProcess(process);
	    this.processesTableModel.fireTableDataChanged();
	}
	
	public HashMap<String, Number> startProcesses(String scheduler){
		if (!Scheduler.PROCESSES.isEmpty()) {
			HashMap<String, Number> schedulingResults = new HashMap<>();
	        processesTableModel.clearProcesses();
	        ArrayList<Thread> threads = new ArrayList<>();
	        
	        int cores = Math.min(Scheduler.PROCESSES.size(), Processor.getProcessorsNumbers());
	        
	        for (int core = 1; core <= cores; core++) {
	            Thread threadScheduler = new Thread(SchedulerFactory.createScheduler(scheduler, this));
	            threads.add(threadScheduler);
	            threadScheduler.start();
	        }

	        // Aguardar todas as threads concluírem
	        for (Thread thread : threads) {
	            try {
	                thread.join();
	            } catch (InterruptedException ex) {
	                throw new RuntimeException(ex);
	            }
	        }
	        
	        processesTableModel.fireTableDataChanged();
	        
	        /* Respostas advindas da execução dos processos */
	        schedulingResults.put("totalTime", Scheduler.executionTimeSpent);
	        schedulingResults.put("averageExecutionTime", Scheduler.calculateAverageTimeSpent());
	        schedulingResults.put("contextChangeCounter", Scheduler.contextChangeCounter);
	        schedulingResults.put("averageWaitingTime", Scheduler.calculateAverageWaitingTime());
	        
	        // Resetando os atributos
            Scheduler.resetAttributes();
            
	        return schedulingResults;
	    } else {
	        processesTableModel.clearProcesses();
	        throw new IllegalStateException("Não há processos na fila!");
	    }
	}
	
	public synchronized void addProcessInView(Process process) {
		this.processesTableModel.addProcess(process);
		this.processesTableModel.fireTableDataChanged();
	}
	
	public synchronized void updateProcessInView(Process process, String status) {
		process.setStatus(status);
		this.processesTableModel.addProcess(process);
		processesTableModel.fireTableDataChanged();
	}
	
	public synchronized void clearProcessesInView() {
        processesTableModel.clearProcesses();
        processesTableModel.fireTableDataChanged();
    }
}
