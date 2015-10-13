package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * SpringとQuartzの連携でDataSourceを使ってみるものです。
 * このテストを3つくらい同時に動かして1つ落としてみるとかで挙動が確認できます。
 *
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JdbcTest {

    @Autowired
    Scheduler scheduler;

    @Test
    public void test() throws Exception {
        TimeUnit.SECONDS.sleep(120);
    }

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            // 実験用に雑にDataSourceを定義します
            return new DriverManagerDataSource("jdbc:hsqldb:hsql://localhost/", "SA", null);
        }

        @Bean
        public JobDetailFactoryBean jobDetail() {
            // job名を指定しなければbean名です。すなわち、このメソッド名です。
            JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
            factoryBean.setJobClass(HogeJob.class);
            factoryBean.setDurability(true);
            return factoryBean;
        }

        @Bean
        public CronTriggerFactoryBean cronTrigger(JobDetail jobDetail) {
            // cron名を指定しなければbean名です。すなわち、このメソッド名です。
            CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
            factoryBean.setCronExpression("*/2 * * * * ?");
            factoryBean.setJobDetail(jobDetail);
            return factoryBean;
        }

        @Bean
        public SchedulerFactoryBean scheduler(JobDetail jobDetail, Trigger trigger) {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            // Springが持っているDataSourceを持ってきて放り込む感じです。
            // JavaConfigなので直接メソッド呼び出ししてますが、引数でも良いし、
            // XMLでやるならrefとかで書く感じになるかと。
            factory.setDataSource(dataSource());

            // 動かすために必要な最低限の設定
            // quartz.propertiesとか読んでもいいけど、設定箇所増えると煩雑になるので。
            // schedulerNameをキーに排他したり登録されているjob/triggerをとったりするので、統一する。
            factory.setSchedulerName("MySpringJdbcScheduler");
            Properties properties = new Properties();
            properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
            properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.HSQLDBDelegate");
            properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
            properties.setProperty("org.quartz.jobStore.isClustered", "true");
            factory.setQuartzProperties(properties);

            // UsingJobDataで設定したJobのプロパティがデフォルトのJobFactoryのままだと設定されないので明示的に指定する
            factory.setJobFactory(new SpringBeanJobFactory());

            // 同じ名前のjob/triggerが登録されてる場合はDBが優先され、更新されたりしないです。
            factory.setJobDetails(jobDetail);
            factory.setTriggers(trigger);

            // 完了まで待った方が変なことになりづらそう
            factory.setWaitForJobsToCompleteOnShutdown(true);
            return factory;
        }
    }

    @DisallowConcurrentExecution
    public static class HogeJob implements Job {
        private static final Logger logger = LoggerFactory.getLogger(HogeJob.class);
        private Object id;

        public void setId(Object id) {
            this.id = id;
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            logger.info("execute HOGE JOB: {}", id);
        }
    }
}
