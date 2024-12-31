package controller;

import views.ProcessesTableModel;

import java.util.ArrayList;
import java.util.HashMap;

import factories.SchedulerFactory;
import models.Process;
import models.Processor;
import schedulers.Scheduler;

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
	        
	        for (int core = 1; core <= Processor.getProcessorsNumbers(); core++) {
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
	        schedulingResults.put("totalTime", Scheduler.executionTimeSpent);
	        schedulingResults.put("averageTime", Scheduler.calculateAverageTimeSpent());
	        schedulingResults.put("contextChangeCounter", Scheduler.contextChangeCounter);
	        
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
