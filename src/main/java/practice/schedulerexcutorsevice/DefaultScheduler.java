package practice.schedulerexcutorsevice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DefaultScheduler {

    private final ScheduledExecutorService scheduledExecutorService=Executors.newSingleThreadScheduledExecutor(r -> new Thread(r,"scheduler"));

    public ElectionTimeOutFuture electionTimeOutFuture(Runnable runnable){
        ScheduledFuture<?> schedule = scheduledExecutorService.scheduleWithFixedDelay(runnable, 1,3, TimeUnit.SECONDS);
        return new ElectionTimeOutFuture(schedule);
    }


}
