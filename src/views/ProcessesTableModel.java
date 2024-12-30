package views;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import models.Process;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ProcessesTableModel extends AbstractTableModel{
	private final List<Process> processes;
    private final String[] columnNames = { "PID", "Status", "Arrival Time", "Execution Time" };
	
    public void clearProcesses() {
    	this.processes.clear();
    	this.fireTableDataChanged();
    }
    
    public ProcessesTableModel(List<Process> processes) {
    	this.processes = processes;
    }
    
    public synchronized void addProcess(Process process) {
    	Process cloneProcess = process.clone();
    	this.processes.add(cloneProcess);
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
	
	public static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value != null && column == 1) {
                String status = value.toString();
                switch (status) {
                    case "Encerrado" -> cell.setBackground(Color.RED);
                    case "Pronto" -> cell.setBackground(Color.YELLOW);
                    case "Executando" -> cell.setBackground(Color.GREEN);
                    default -> cell.setBackground(Color.WHITE); //
                }
                cell.setForeground(Color.BLACK);  
            } else {
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
            }

            return cell;
        }
    }
}

