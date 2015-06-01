package example4;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Jobにアノテーションを付与することで制御する。
 * {@link DisallowConcurrentExecution}をつけるとJobBuilderで作られた単位で同時実行されない。
 * {@link PersistJobDataAfterExecution}をつけると次実行されるときに引き継がれる。
 * これらの機能は現在Deprecatedになっている{@link StatefulJob}で実装していたものと思われる。
 *
 * @author irof
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MyJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(MyJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            int count = jobDataMap.getInt("hoge");

            LOG.info("starting: {}", count);

            jobDataMap.put("hoge", count + 1);
            TimeUnit.SECONDS.sleep(5);

            LOG.info("finish: {} to {}", count, jobDataMap.getInt("hoge"));
        } catch (InterruptedException e) {
            throw new JobExecutionException(e);
        }
    }
}
