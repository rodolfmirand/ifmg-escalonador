package factories;

import schedulers.RoundRoubin;
import schedulers.Scheduler;
import schedulers.ShortestJobFirst;
import views.ProcessesTableModel;

public class SchedulerFactory {
	public static Scheduler createScheduler(String scheduler, ProcessesTableModel processTableModel) {
		return switch(scheduler) {
			case "Round Roubin" -> new RoundRoubin(processTableModel);
			default -> new ShortestJobFirst(processTableModel);
		};
	}
}
