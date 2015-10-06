package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
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
            // JobDetailに設定するJobClassは Class<? extends Job> でないといけない。
            // JobDetailFactoryBean#setJobが何も指定されていないのは、XMLで使うものだからだろうかね。
            // QuartzJobBeanの継承クラスで作ってるのはJobDataAsMapで設定した値を設定させたいから。
            JobDetailFactoryBean factory = new JobDetailFactoryBean();
            factory.setJobClass(ScheduledJobBean.class);
            factory.setJobDataAsMap(Collections.singletonMap("latch", latch()));
            factory.setDurability(true);
            return factory;
        }

        @Bean
        public CronTriggerFactoryBean triggerFactory(JobDetail jobDetail) {
            CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
            factory.setJobDetail(jobDetail);
            factory.setStartDelay(1000);
            factory.setCronExpression("*/2 * * * * ?");
            return factory;
        }

        @Bean
        public SchedulerFactoryBean schedulerFactory(JobDetail jobDetail, Trigger trigger) {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setJobDetails(jobDetail);
            factory.setTriggers(trigger);
            return factory;
        }
    }
}
