package spring;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author irof
 */
public class ScheduledJobBean extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger("spring");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Hello, Scheduled Job: {}, context: {}", this, context);

    }
}
