package schedulers;

import controller.SchedulerController;
import models.Process;
import models.Processor;
import views.ProcessesTableModel;

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
			
			currentTime += executionTime;
			process.setWaitingTime(currentTime - executionTime - process.getArrivalTime());
			
			String status = process.getSpentTime() > 0 ? "Pronto" : "Encerrado";
			this.addProcess(process, status);
			this.schedulerController.updateProcessInView(process, status);
		}
	}
}
