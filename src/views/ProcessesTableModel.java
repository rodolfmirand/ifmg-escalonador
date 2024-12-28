package views;

import java.util.List;
import models.Process;
import javax.swing.table.AbstractTableModel;

public class ProcessesTableModel extends AbstractTableModel{
	private final List<Process> processes;
    private final String[] columnNames = { "PID", "Status", "Arrival Time", "Execution Time" };
	
    public ProcessesTableModel(List<Process> processes) {
    	this.processes = processes;
    }
    
    public void addProcess(Process process) {
    	this.processes.add(process);
    }
    
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return this.processes.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Process process = this.processes.get(rowIndex);
		return switch(columnIndex) {
			case 0 -> process.getPid();
			case 1 -> process.getStatus();
			case 2 -> process.getArrivalTime();
			case 3 -> process.getSpentTime();
			default -> null;
		};
	}
	
	 @Override
	    public String getColumnName(int column) {
	        return columnNames[column];
	    }
}
