package schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.Process;

public abstract class Scheduler implements Runnable{
	public static final List<Process> PROCESSES = new ArrayList<>();
	public abstract void run();
	
	protected void sortByArrivalTime() {
		PROCESSES.sort(Comparator.comparingInt(Process::getArrivalTime));
	}
}
