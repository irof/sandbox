package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * POJOをSimpleTriggerで実行するサンプルですよ。
 * 参考: http://websystique.com/spring/spring-4-quartz-scheduler-integration-example/
 *
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MethodInvokingJobDetailAndSimpleTriggerTest {

    @Test
    public void test() throws Exception {
        TimeUnit.SECONDS.sleep(5);
    }

    @Configuration
    static class Config {

        @Bean
        public PojoJobBean myPojo() {
            return new PojoJobBean();
        }

        @Bean
        public MethodInvokingJobDetailFactoryBean pojoDetailFactory() {
            MethodInvokingJobDetailFactoryBean factory = new MethodInvokingJobDetailFactoryBean();
            factory.setTargetObject(myPojo());
            factory.setTargetMethod("hello");
            return factory;
        }

        @Bean
        public SimpleTriggerFactoryBean triggerFactory() {
            SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
            factory.setJobDetail(pojoDetailFactory().getObject());
            factory.setStartDelay(1000);
            factory.setRepeatInterval(2000);
            return factory;
        }

        @Bean
        public SchedulerFactoryBean schedulerFactory() {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setJobDetails(pojoDetailFactory().getObject());
            factory.setTriggers(triggerFactory().getObject());
            return factory;
        }
    }
}
