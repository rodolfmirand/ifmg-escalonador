package factories;

import controller.SchedulerController;
import schedulers.RoundRoubin;
import schedulers.Scheduler;
import schedulers.ShortestJobFirst;
import views.ProcessesTableModel;

public class SchedulerFactory {
	public static Scheduler createScheduler(String scheduler, SchedulerController schedulerController) {
		return switch(scheduler) {
			case "Round Roubin" -> new RoundRoubin(schedulerController);
			default -> new ShortestJobFirst(schedulerController);
		};
	}
}
