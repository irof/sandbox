package hello;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 一つのJobをいろんなTriggerで実行する。
 *
 * @author irof
 */
public class TriggersSample {
    public static void main(String... args) throws Exception {

        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "myGroup")
                .build();

        // 2秒間隔で3回動くTrigger
        Trigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("mySimpleTrigger", "myGroup")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(3, 2))
                .build();

        // 5秒ごとに動くTrigger
        Trigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("myCronTrigger", "myGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .build();

        // 次の分に開始するTrigger
        // dailyなので次に動くのは翌日
        LocalTime time = LocalTime.now().plusMinutes(1);
        Trigger dailyTrigger = TriggerBuilder.newTrigger()
                .withIdentity("myDailyTrigger", "myGroup")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(time.getHour(), time.getMinute()))
                .build();

        Set<Trigger> triggers = new HashSet<>();
        triggers.add(simpleTrigger);
        triggers.add(cronTrigger);
        triggers.add(dailyTrigger);

        CountDownJobListener countDownJobListener = new CountDownJobListener(10);

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, triggers, false);
        scheduler.getListenerManager().addJobListener(countDownJobListener);
        scheduler.start();

        countDownJobListener.await();

        // 直接起動
        scheduler.triggerJob(jobDetail.getKey());

        scheduler.shutdown(true);
    }
}
