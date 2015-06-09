import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * SimpleScheduleBuilderを使用したTriggerの設定。
 * 実行間隔や繰り返し回数をメソッドチェーンで指定する。
 *
 * @author irof
 */
public class SimpleTriggerSample {

    public static void main(String... args) throws Exception {
        HelloJob.testLatch = new CountDownLatch(10);
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .forJob(job)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMilliseconds(500)
                                .repeatForever()
                )
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();

        // 満足するまで待つ
        HelloJob.testLatch.await();
        scheduler.shutdown(true);
    }
}
