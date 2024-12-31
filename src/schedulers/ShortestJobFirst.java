package schedulers;

import controller.SchedulerController;
import models.Process;
import views.ProcessesTableModel;

public class ShortestJobFirst extends Scheduler{
	public ShortestJobFirst(SchedulerController schedulerController) {
		super(schedulerController);
	}
	
	public void run() {
		int currentTime = 0;
		while(!this.processesIsEmpty()) {
			Process process = this.getProcess();
			
			this.schedulerController.addProcessInView(process);
			Scheduler.setExecutionTimeSpent(process.getSpentTime());
			
			currentTime += process.getSpentTime();
			
			process.setWaitingTime(currentTime - process.getArrivalTime());
			
			this.finishProcess(process);
			this.schedulerController.addProcessInView(process);
		}
	}
	
	private void finishProcess(Process process) {
		process.setSpentTime(0);
		process.setStatus("Encerrado");
		this.addProcess(process, "Encerrado");
	}
}
