package factories;

import controller.SchedulerController;
import schedulers.RoundRoubin;
import schedulers.Scheduler;
import schedulers.ShortestJobFirst;

public class SchedulerFactory {
    public static Scheduler createScheduler(String scheduler, SchedulerController schedulerController) {
        if (scheduler.equals("Round Robin")) {
            Scheduler.sortByArrivalTime();
            return new RoundRoubin(schedulerController);
        }
        Scheduler.sortByExecutionTime();
        return new ShortestJobFirst(schedulerController);
    }
}