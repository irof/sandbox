package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;

/**
 * QuartzJobをCronTriggerで実行するサンプルですよ。
 * 参考: http://websystique.com/spring/spring-4-quartz-scheduler-integration-example/
 *
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JobDetailAndCronTriggerTest {

    @Autowired
    CountDownLatch latch;

    @Test
    public void test() throws Exception {
        latch.await();
    }

    @Configuration
    static class Config {

        @Bean
        public CountDownLatch latch() {
            // 5回やる
            return new CountDownLatch(5);
        }

        @Bean
        public JobDetailFactoryBean detailFactory() {
            JobDetailFactoryBean factory = new JobDetailFactoryBean();
            factory.setJobClass(ScheduledJobBean.class);
            factory.setJobDataAsMap(Collections.singletonMap("latch", latch()));
            factory.setDurability(true);
            return factory;
        }

        @Bean
        public CronTriggerFactoryBean triggerFactory() {
            CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
            factory.setJobDetail(detailFactory().getObject());
            factory.setStartDelay(1000);
            factory.setCronExpression("*/2 * * * * ?");
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
