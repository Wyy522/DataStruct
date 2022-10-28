package practice.schedulerexcutorsevice;

import java.util.concurrent.ScheduledFuture;

public class LogReplicationFuture {

    private ScheduledFuture<?> scheduledFuture;

    public LogReplicationFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}
