package schedulers;

import models.Process;
import models.Processor;
import views.ProcessesTableModel;

public class RoundRoubin extends Scheduler{
	public RoundRoubin(ProcessesTableModel processTableModel) {
		super(processTableModel);
	}
	
	@Override
	public void run() {
		this.sortByArrivalTime();

		while(!this.processesIsEmpty()) {
			Process process = this.getProcess();

			process.setStatus("Executando");
			this.processTableModel.addProcess(process);
			this.processTableModel.fireTableDataChanged();
			
			int executionTime = Math.min(process.getSpentTime(), Processor.getQuantum());
			
			process.setSpentTime(process.getSpentTime() - executionTime);
			Scheduler.executionTimeSpent += executionTime;
			
			if(process.getSpentTime() > 0) {
				this.addProcess(process);
				process.setStatus("Pronto");
				this.processTableModel.addProcess(process);
			}else {
				process.setStatus("Encerrado");
				this.processTableModel.addProcess(process);
			}

			this.processTableModel.fireTableDataChanged();
		}
	}
}
