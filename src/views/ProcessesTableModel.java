package views;

import java.util.List;
import models.Process;
import javax.swing.table.AbstractTableModel;

public class ProcessesTableModel extends AbstractTableModel{
	private final List<Process> processes;
    private final String[] columnNames = { "PID", "Status", "Arrival Time", "Execution Time", "Quantum" };
	
    public ProcessesTableModel(List<Process> processes) {
    	this.processes = processes;
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
		switch(columnIndex) {
			case 0: return process.getPid();
			case 1: return process.getStatus();
			case 2: return process.getArrivalTime();
			case 3: return process.getSpentTime();
			case 4: return process.getQuantum();
			default: return null;
		}
	}
	
	 @Override
	    public String getColumnName(int column) {
	        return columnNames[column];
	    }
}
