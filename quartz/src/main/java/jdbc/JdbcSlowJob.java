package jdbc;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * {@link Job} をextends
 *
 * @author irof
 */
@DisallowConcurrentExecution
public class JdbcSlowJob implements Job {

    private static Logger logger = LoggerFactory.getLogger("jdbcjob");

    private long sleeps = 5;

    public void setSleeps(long sleeps) {
        this.sleeps = sleeps;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("executing {}... sleep: {} seconds", this, sleeps);

        try {
            TimeUnit.SECONDS.sleep(sleeps);
        } catch (InterruptedException e) {
            throw new JobExecutionException(e);
        }
    }
}
