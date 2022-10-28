package practice.schedulerexcutorsevice;

import java.util.concurrent.ScheduledFuture;

public class ElectionTimeOutFuture {

    private ScheduledFuture<?> scheduledFuture;

    public ElectionTimeOutFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}
