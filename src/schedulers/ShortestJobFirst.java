package schedulers;

import models.Process;
import views.ProcessesTableModel;

public class ShortestJobFirst extends Scheduler{
	public ShortestJobFirst(ProcessesTableModel processTableModel) {
		super(processTableModel);
	}
	
	public void run() {
		this.sortByExecutionTime();

		while(!this.processesIsEmpty()) {
			Process process = this.getProcess();
			process.setStatus("Executando");
			
			this.processTableModel.addProcess(process);
			Scheduler.executionTimeSpent += process.getSpentTime();
			
			process.setSpentTime(0);
			process.setStatus("Encerrado");
			this.processTableModel.addProcess(process);
			
			this.processTableModel.fireTableDataChanged();
		}
	}
}
