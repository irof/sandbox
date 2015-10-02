package jdbc;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * jdbcjobstoreを使うSchedulerを実行する。
 * このクラス単体で実行しても何も起こらないけれど、{@link JobRegistrant} を実行すると、
 * JobがDBに登録されて、それをこいつが実行する事になる。
 *
 * こいつを複数起動すると、複数同時起動の確認もできる。
 *
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
