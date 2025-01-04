package schedulers;

import controller.SchedulerController;
import models.Process;
import models.Processor;
import views.components.ProcessesTableModel;

public class RoundRoubin extends Scheduler{
	public RoundRoubin(SchedulerController schedulerController) {
		super(schedulerController);
	}
	
	@Override
	public void run() {
		int currentTime = 0;
		while(!this.processesIsEmpty()) {
			Process process = this.getProcess();
			
			this.schedulerController.addProcessInView(process);
			
			int executionTime = Math.min(process.getSpentTime(), Processor.getQuantum());
			
			process.setSpentTime(process.getSpentTime() - executionTime);
			Scheduler.setExecutionTimeSpent(executionTime);
			
			process.setWaitingTime(currentTime - process.getLastExecutionTime());
			currentTime += executionTime;
			process.setLastExecutionTime(currentTime);
			
			String status = process.getSpentTime() > 0 ? "Pronto" : "Encerrado";
			this.updateProcess(process, status);
			this.schedulerController.updateProcessInView(process, status);
		}
	}
}
