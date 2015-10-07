package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
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
        latch.await();

        assert latch.getCount() == 0;
    }

    @Configuration
    static class Config {

        @Bean
        public CountDownLatch latch() {
            // 5回やる
            return new CountDownLatch(5);
        }

        @Bean
        public SamplePojo myPojo() {
            return new SamplePojo();
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
            // どこにでもある当たり前のトリガー 0.5秒間隔 で実行する。
            SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
            factory.setJobDetail(jobDetail);
            factory.setStartDelay(1000);
            factory.setRepeatInterval(500);
            // MethodInvokingJobDetailFactoryBeanを使用するとこの値は設定されない
            factory.setJobDataAsMap(Collections.singletonMap("triggerParam", "HOGE"));
            return factory;
        }

        /**
         * 引数にはJobDetailとtriggerのFactoryBeanから生成されたオブジェクトが入ってくるです。
         * afterPropertiesSetでそれぞれのインスタンス生成をしているので、
         * FactoryBeanを使う場合は各々Bean定義する必要があります。
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

    /**
     * Jobインタフェースを実装しない、よくあるSingletonで定義されるサービスクラスみたいなやつ
     */
    public static class SamplePojo {

        @Autowired
        CountDownLatch latch;

        private static Logger logger = LoggerFactory.getLogger("spring");

        private String triggerParam = "WORLD";

        public void setTriggerParam(String triggerParam) {
            this.triggerParam = triggerParam;
        }

        public void hello() {
            logger.info("Hello, {}!", triggerParam);
            logger.info("  This is POJO Job: {}.", this);
            logger.info("  latch count is {}.", latch.getCount());
            latch.countDown();
        }
    }
}
