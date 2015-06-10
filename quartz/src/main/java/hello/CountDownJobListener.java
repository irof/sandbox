package hello;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

import java.util.concurrent.CountDownLatch;

/**
 * @author irof
 */
class CountDownJobListener extends JobListenerSupport implements AutoCloseable {
    private final CountDownLatch latch;

    public CountDownJobListener(CountDownLatch latch) {
        this.latch = latch;
    }

    public CountDownJobListener(int count) {
        this(new CountDownLatch(count));
    }

    @Override
    public String getName() {
        return "countDownListener";
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    @Override
    public void close() throws Exception {
        await();
    }
}
