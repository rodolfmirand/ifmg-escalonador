package models;

public class Process {
	private int pid;
	private int arrivalTime;
	private int spentTime;
	private int quantum;
	private String status;
	
	public Process(int pid, int arrivalTime, int spentTime, int quantum) {
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		this.spentTime = spentTime;
		this.status = "ready";
		this.quantum = quantum;
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
	
	public int getQuantum() {
		return this.quantum;
	}
	
	public void setSpentTime(int spentTime) {
		this.spentTime = spentTime;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return "PID: " + this.pid + " | Status: "+ this.status +" | Arrival Time: " + this.arrivalTime + " | Spent Time: " + this.spentTime + "\n";
	}
}
