package practice.excutorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class TaskExecutor {

    private ExecutorService executorService= Executors.newSingleThreadExecutor();

    public Future<?> submit(Runnable runnable){
        return executorService.submit(runnable::run);
    }

}
