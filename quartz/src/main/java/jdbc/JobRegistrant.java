package jdbc;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * トリガーだけ登録してさっさと終了しちゃう子。
 * 実行は {@link JobStarter} ががんばる。
 *
 * @author irof
 */
public class JobRegistrant {
    public static void main(String[] args) throws Exception {
        System.setProperty(StdSchedulerFactory.PROPERTIES_FILE,
                ClassLoader.getSystemResource("jdbc/quartz.properties").getPath());

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        try (AutoCloseable ac = scheduler::shutdown) {
            scheduler.clear();
            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("myCronTrigger1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("*/3 * * * * ?"))
                    .build();
            scheduler.scheduleJob(JobBuilder.newJob(JdbcSlowJob.class)
                    .withIdentity("myJob1")
                    .usingJobData("sleeps", 10L)
                    .storeDurably(true)
                    .build(), trigger1);
        }
    }
}
