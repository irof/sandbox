package hello;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;

/**
 * CronScheduleBuilderを使用したTriggerの設定。
 * cronの書き方で指定したり、メソッドで指定したり。
 *
 * @author irof
 */
public class CronTriggerSample {

    public static void main(String... args) throws Exception {
        HelloJob.testLatch = new CountDownLatch(15);

        // 5秒ごとに動くTrigger
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .build();

        // 次の分に開始するTrigger
        // dailyなので次に動くのは翌日
        LocalTime time = LocalTime.now().plusMinutes(1);
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group2")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(time.getHour(), time.getMinute()))
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(JobBuilder.newJob(HelloJob.class).build(), trigger1);
        scheduler.scheduleJob(JobBuilder.newJob(HelloJob.class).build(), trigger2);
        scheduler.start();

        // 満足するまで待つ
        HelloJob.testLatch.await();
        scheduler.shutdown(true);
    }
}
