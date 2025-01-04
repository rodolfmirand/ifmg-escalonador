package schedulers;

import controller.SchedulerController;
import models.Process;
import views.components.ProcessesTableModel;

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
			
			process.setWaitingTime(currentTime);
			currentTime += process.getSpentTime();
			process.setLastExecutionTime(currentTime);
			
			this.finishProcess(process);
			this.schedulerController.addProcessInView(process);
		}
	}
	
	private void finishProcess(Process process) {
		process.setSpentTime(0);
		process.setStatus("Encerrado");
		this.updateProcess(process, "Encerrado");
	}
}
