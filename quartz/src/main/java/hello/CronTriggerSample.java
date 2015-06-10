package hello;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * CronScheduleBuilderを使用したTriggerの設定。
 * 3秒毎に動くJobをcronで指定する。
 *
 * @author irof
 */
public class CronTriggerSample {

    public static void main(String... args) throws Exception {
        CountDownJobListener countDownJobListener = new CountDownJobListener(5);

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(
                newJob(HelloJob.class).withIdentity("myJob", "myGroup")
                        .storeDurably()
                        .build(),
                newTrigger().withIdentity("myCronTrigger", "myGroup")
                        .withSchedule(cronSchedule("*/3 * * * * ?"))
                        .build()
        );

        scheduler.getListenerManager().addJobListener(countDownJobListener);
        scheduler.start();

        countDownJobListener.await();
        scheduler.shutdown(true);
    }
}
