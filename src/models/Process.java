package models;

public class Process implements Cloneable{
	private int pid;
	private int arrivalTime;
	private int spentTime;
	private int waitingTime;
	private int lastExecutionTime;
	private String status;
	
	public Process(int pid, int arrivalTime, int spentTime) {
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		this.spentTime = spentTime;
		this.status = "Pronto";
		this.waitingTime = 0;
		this.lastExecutionTime = arrivalTime;
	}
	
	public String getStatus() {
		return this.status;
	}

	public int getPid() {
		return this.pid;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getSpentTime() {
		return this.spentTime;
	}
	
	public void setSpentTime(int spentTime) {
		this.spentTime = spentTime;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getWaitingTime() {
		return this.waitingTime;
	}
	
	public void setWaitingTime(int waitingTime) {
		this.waitingTime += waitingTime;
	}
	
	
	public int getLastExecutionTime() {
		return this.lastExecutionTime;
	}

	public void setLastExecutionTime(int lastExecutionTime) {
		this.lastExecutionTime = lastExecutionTime;
	}

	public String toString() {
		return "PID: " + this.pid + " | Status: "+ this.status +" | Arrival Time: " + this.arrivalTime + " | Spent Time: " + this.spentTime + "\n";
	}
	
	@Override
	public Process clone() {
		try {
			return (Process) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
