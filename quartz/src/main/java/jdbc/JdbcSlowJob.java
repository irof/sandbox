package jdbc;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 「そこそこ時間のかかる処理」をイメージした指定秒数sleepするJobの実装。
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
        logger.info("executing job:{{}}, context:{{}}", this, context);

        try {
            TimeUnit.SECONDS.sleep(sleeps);
        } catch (InterruptedException e) {
            throw new JobExecutionException(e);
        } finally {
            logger.info("finish job:{{}}, context:{{}}", this, context);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ": sleeps:" + sleeps;

    }
}
