package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.simpl.PropertySettingJobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * SpringとQuartzの連携でbean登録されているDataSourceを使ってみるものです。
 * DBはベット起動する必要があります。
 * また、DBにjobが入っていないと何も起こりません。
 *
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JdbcTest {

    @Test
    public void test() throws Exception {
        TimeUnit.SECONDS.sleep(20);
    }

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            return new DriverManagerDataSource("jdbc:hsqldb:hsql://localhost/", "SA", null);
        }

        @Bean
        public SchedulerFactoryBean scheduler() {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            // Springが持っているDataSourceを持ってきて放り込む感じです。
            // JavaConfigなので直接メソッド呼び出ししてますが、引数でも良いし、
            // XMLでやるならrefとかで書く感じになるかと。
            factory.setDataSource(dataSource());

            // 動かすために必要な最低限の設定
            // quartz.propertiesとか読んでもいいけど、設定箇所増えると煩雑になるので。
            factory.setSchedulerName("MyJdbcScheduler");
            Properties properties = new Properties();
            properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
            properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.HSQLDBDelegate");
            properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
            properties.setProperty("org.quartz.jobStore.isClustered", "true");
            factory.setQuartzProperties(properties);

            // UsingJobDataで設定したJobのプロパティだけど、SchedulerFactoryBeanのデフォルトである
            // AdaptableJobFactoryだと設定されない。
            // 正道はよくわかってないけれど、とりあえずこれで動いてはいる。
            factory.setJobFactory(new PropertySettingJobFactory());
            return factory;
        }
    }
}
