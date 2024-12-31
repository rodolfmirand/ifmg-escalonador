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
		while(!this.processesIsEmpty()) {
			Process process = this.getProcess();

			process.setStatus("Executando");
			this.schedulerController.addProcessInView(process);
			
			int executionTime = Math.min(process.getSpentTime(), Processor.getQuantum());
			
			process.setSpentTime(process.getSpentTime() - executionTime);
			Scheduler.setExecutionTimeSpent(executionTime);
			
			if(process.getSpentTime() > 0) {
				this.addProcess(process);
				this.schedulerController.updateProcessInView(process, "Pronto");
			}else {
				this.schedulerController.updateProcessInView(process, "Encerrado");
			}
		}
	}

	@Override
	public double calculateWaitingTime() {
		return 0;
	}
}
