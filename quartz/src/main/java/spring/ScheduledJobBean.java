package spring;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.CountDownLatch;

/**
 * @author irof
 */
public class ScheduledJobBean extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger("spring");
    private CountDownLatch latch;

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        latch.countDown();
        logger.info("Hello, Scheduled Job: {}, context: {}", this, context);
    }
}
