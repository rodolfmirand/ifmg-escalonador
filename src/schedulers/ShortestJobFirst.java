package schedulers;

import controller.SchedulerController;
import models.Process;
import views.ProcessesTableModel;

public class ShortestJobFirst extends Scheduler{
	public ShortestJobFirst(SchedulerController schedulerController) {
		super(schedulerController);
	}
	
	public void run() {
		while(!this.processesIsEmpty()) {
			Process process = this.getProcess();
			process.setStatus("Executando");
			
			this.schedulerController.addProcessInView(process);
			Scheduler.setExecutionTimeSpent(process.getSpentTime());
			
			process.setSpentTime(0);
			process.setStatus("Encerrado");
			this.schedulerController.addProcessInView(process);
		}
	}

	@Override
	public double calculateWaitingTime() {
		double totalProcessWatintTime = 0;
		for(Process process : Scheduler.PROCESSES) {
			totalProcessWatintTime += process.getSpentTime() - process.getArrivalTime();
		}
		
		return totalProcessWatintTime/Scheduler.processCounter;
	}
}
