package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * QuartzJobをCronTriggerで実行するサンプルですよ。
 * 参考: http://websystique.com/spring/spring-4-quartz-scheduler-integration-example/
 *
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JobDetailAndCronTriggerTest {

    @Test
    public void test() throws Exception {
        TimeUnit.SECONDS.sleep(20);
    }

    @Configuration
    static class Config {

        @Bean
        public JobDetailFactoryBean detailFactory() {
            JobDetailFactoryBean factory = new JobDetailFactoryBean();
            factory.setJobClass(ScheduledJobBean.class);
            factory.setDurability(true);
            return factory;
        }

        @Bean
        public CronTriggerFactoryBean triggerFactory() {
            CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
            factory.setJobDetail(detailFactory().getObject());
            factory.setStartDelay(1000);
            factory.setCronExpression("*/5 * * * * ?");
            return factory;
        }

        @Bean
        public SchedulerFactoryBean schedulerFactory() {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setJobDetails(detailFactory().getObject());
            factory.setTriggers(triggerFactory().getObject());
            return factory;
        }
    }
}
