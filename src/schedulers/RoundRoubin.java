package schedulers;

import models.Process;
import models.Quantum;
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

			process.setStatus("Running");
			int executionTime = Math.min(process.getSpentTime(), Quantum.getValue());
			
			process.setSpentTime(process.getSpentTime() - executionTime);
			Scheduler.executionTimeSpent += executionTime;
			
			if(process.getSpentTime() > 0) {
				this.addProcess(process);
				process.setStatus("Ready");
			}

			this.processTableModel.fireTableDataChanged();
		}
	}
}
