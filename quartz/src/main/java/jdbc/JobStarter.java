package jdbc;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author irof
 */
public class JobStarter {

    public static void main(String[] args) throws SchedulerException {
        System.setProperty(StdSchedulerFactory.PROPERTIES_FILE,
                ClassLoader.getSystemResource("jdbc/quartz.properties").getPath());

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
    }
}
