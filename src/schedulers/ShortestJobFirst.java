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
			process.setStatus("Running");
			Scheduler.executionTimeSpent += process.getSpentTime();
			
			System.out.println(process.getPid());
			
			process.setSpentTime(0);
			this.processTableModel.fireTableDataChanged();
		}
	}
}
