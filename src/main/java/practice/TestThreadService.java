package practice;

import practice.excutorservice.TaskExecutor;
import practice.schedulerexcutorsevice.DefaultScheduler;

import java.util.concurrent.Future;

public class TestThreadService {
    public static void main(String[] args) {
        TestThreadService testThreadService = new TestThreadService();
        new Thread(testThreadService::TestTimeOut).start();
        System.out.println("2");
//       new Thread(()->{
//          testThreadService.TestTimeOut();
//       }).start();
    }

    public void TestTimeOut() {
        DefaultScheduler defaultScheduler = new DefaultScheduler();
        defaultScheduler.electionTimeOutFuture(this::doTimeOut);
    }

    public void doTimeOut() {
        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.submit(this::print);
    }

    public void print() {
        System.out.println("1");
    }
}
