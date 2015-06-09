package jdbc;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author irof
 */
public class JobRegistrant {
    public static void main(String[] args) throws SchedulerException {
        System.setProperty(StdSchedulerFactory.PROPERTIES_FILE,
                ClassLoader.getSystemResource("jdbc/quartz.properties").getPath());

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.clear();
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("myCronTrigger1", "myJdbcJobGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/3 * * * * ?"))
                .build();
        scheduler.scheduleJob(JobBuilder.newJob(JdbcSlowJob.class)
                .usingJobData("sleeps", 10L)
                .storeDurably(true)
                .build(), trigger1);

        scheduler.shutdown();
    }
}
