package hello;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalTime;

/**
 * CronScheduleBuilderを使用したTriggerの設定。
 * cronの書き方で指定したり、メソッドで指定したり。
 *
 * @author irof
 */
public class CronTriggerSample {

    public static void main(String... args) throws Exception {

        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "myGroup")
                .storeDurably()
                .build();
        // 5秒ごとに動くTrigger
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("myCronTrigger", "myGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .forJob(jobDetail)
                .build();

        // 次の分に開始するTrigger
        // dailyなので次に動くのは翌日
        LocalTime time = LocalTime.now().plusMinutes(1);
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("myDailyTrigger", "myGroup")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(time.getHour(), time.getMinute()))
                .forJob(jobDetail)
                .build();

        CountDownJobListener countDownJobListener = new CountDownJobListener(10);

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.addJob(jobDetail, false);
        scheduler.scheduleJob(trigger1);
        scheduler.scheduleJob(trigger2);
        scheduler.getListenerManager().addJobListener(countDownJobListener);
        scheduler.start();

        // 直接起動
        scheduler.triggerJob(jobDetail.getKey());

        countDownJobListener.await();
        scheduler.shutdown(true);
    }
}
