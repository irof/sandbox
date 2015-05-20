import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * CronScheduleBuilderを使用したTriggerの設定。
 * cronの書き方で指定したり、メソッドで指定したり。
 *
 * @author irof
 */
public class CronTriggerSample {

    public static void main(String... args) throws Exception {
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .build();

        // 次の分に開始するTrigger
        LocalTime time = LocalTime.now().plusMinutes(1);
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group2")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(time.getHour(), time.getMinute()))
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(JobBuilder.newJob(HelloJob.class).build(), trigger1);
        scheduler.scheduleJob(JobBuilder.newJob(HelloJob.class).build(), trigger2);
        scheduler.start();
        TimeUnit.SECONDS.sleep(70);
        scheduler.shutdown(true);
    }
}
