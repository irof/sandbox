import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author irof
 */
public class HelloJob implements Job {

    /**
     * 満足する回数のlatchを放り込む
     */
    static CountDownLatch testLatch;

    private static Logger logger = LoggerFactory.getLogger("sample");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        testLatch.countDown();

        logger.info("context: {}", context);
    }
}

