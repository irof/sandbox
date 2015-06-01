package example4;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 同時実行制御とパラメータ引き継ぎのサンプル。
 * http://quartz-scheduler.org/generated/2.2.1/html/qs-all/#page/Quartz_Scheduler_Documentation_Set%2Fre-exp_example4.html%23
 *
 * @author irof
 */
public class ParameterAndStateSample {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        JobDetail job1 = JobBuilder.newJob(MyJob.class)
                .usingJobData("hoge", 10)
                .withIdentity("hoge", "foo")
                .build();
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2)
                                .withRepeatCount(2)
                )
                .build();
        JobDetail job2 = JobBuilder.newJob(MyJob.class)
                .usingJobData("hoge", 20)
                .withIdentity("fuga", "baz")
                .build();
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(1)
                                .withRepeatCount(2)
                )
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job1, trigger1);
        scheduler.scheduleJob(job2, trigger2);
        scheduler.start();

        TimeUnit.SECONDS.sleep(20);

        scheduler.shutdown(true);
    }
}
