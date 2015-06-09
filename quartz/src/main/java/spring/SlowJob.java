package spring;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * {@link Job} をextends
 *
 * @author irof
 */
@DisallowConcurrentExecution
public class SlowJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger("spring");

    private CountDownLatch latch;
    private long sleeps = 5;

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        run();
    }

    private void run() throws JobExecutionException {
        latch.countDown();
        logger.info("executing {}... sleep: {} seconds, latch: {}", this, sleeps, latch.getCount());

        try {
            TimeUnit.SECONDS.sleep(sleeps);
        } catch (InterruptedException e) {
            throw new JobExecutionException(e);
        }
    }
}
