package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * POJOをSimpleTriggerで実行するサンプルですよ。
 * 参考: http://websystique.com/spring/spring-4-quartz-scheduler-integration-example/
 *
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MethodInvokingJobDetailAndSimpleTriggerTest {

    @Autowired
    CountDownLatch latch;

    @Test
    public void test() throws Exception {
        // 5回実行するまで待機する
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
        public PojoJobBean myPojo() {
            return new PojoJobBean();
        }

        @Bean
        public MethodInvokingJobDetailFactoryBean detailFactory() {
            // 指定したメソッドをリフレクションで実行するJobインスタンスを作成するファクトリ
            MethodInvokingJobDetailFactoryBean factory = new MethodInvokingJobDetailFactoryBean();
            factory.setTargetObject(myPojo());
            factory.setTargetMethod("hello");
            return factory;
        }

        @Bean
        public SimpleTriggerFactoryBean triggerFactory(JobDetail jobDetail) {
            // どこにでもある当たり前のトリガー
            SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
            factory.setJobDetail(jobDetail);
            factory.setStartDelay(1000);
            factory.setRepeatInterval(500);
            return factory;
        }

        /**
         * 引数にはJobDetailとtriggerのFactoryBeanから生成されたオブジェクトが入ってくるです。
         * afterPropertiesSetでそれぞれのインスタンス生成をしているので、
         * FactoryBeanを使う場合は各々Bean定義しないダメです。
         * ……とはいえ、FactoryBeanはXMLで書くためのものなんで、無理に使う必要ないです。
         */
        @Bean
        public SchedulerFactoryBean schedulerFactory(JobDetail jobDetail, Trigger trigger) {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setJobDetails(jobDetail);
            factory.setTriggers(trigger);
            return factory;
        }
    }
}
