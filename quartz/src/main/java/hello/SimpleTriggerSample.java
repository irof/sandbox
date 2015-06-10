package hello;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * SimpleScheduleBuilderを使用したTriggerの設定。
 * 実行間隔や繰り返し回数をメソッドチェーンで指定する。
 *
 * @author irof
 */
public class SimpleTriggerSample {

    public static void main(String... args) throws Exception {

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

        CountDownJobListener countDownJobListener = new CountDownJobListener(5);

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.getListenerManager().addJobListener(countDownJobListener);
        scheduler.start();

        countDownJobListener.await();
        scheduler.shutdown(true);
    }

}
