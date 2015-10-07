package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
            return new CountDownLatch(10);
        }

        @Bean
        public SamplePojo myPojo() {
            return new SamplePojo();
        }

        @Bean
        public MethodInvokingJobDetailFactoryBean hogeDetail() {
            // 指定したメソッドをリフレクションで実行するJobインスタンスを作成するファクトリ
            MethodInvokingJobDetailFactoryBean factory = new MethodInvokingJobDetailFactoryBean();
            factory.setTargetObject(myPojo());
            factory.setTargetMethod("hello");
            return factory;
        }

        @Bean
        public JobDetail fugaDetail() {
            return JobBuilder.newJob(SamplePojoJob.class)
                    .storeDurably()
                    .build();
        }

        @Bean
        public MethodInvokingJobDetailFactoryBean piyoDetail() {
            MethodInvokingJobDetailFactoryBean factory = new MethodInvokingJobDetailFactoryBean();
            // bean名指定でも参照できるけど、これよりもインスタンス見に行く方が良い
            factory.setTargetBeanName("myPojo");
            factory.setTargetMethod("hello");
            return factory;
        }

        @Bean
        public Trigger hogeTrigger(JobDetail hogeDetail) {
            // MethodInvokingJobDetailFactoryBeanを使用しているため、JobDataは使用されません
            return TriggerBuilder.newTrigger()
                    .forJob(hogeDetail)
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                    .usingJobData("triggerParam", "HOGE")
                    .startNow()
                    .build();
        }

        @Bean
        public Trigger fugaTrigger(JobDetail fugaDetail) {
            // Jobインタフェースを使用しているため、JobDataが使用されます
            return TriggerBuilder.newTrigger()
                    .forJob(fugaDetail)
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                    .usingJobData("triggerParam", "FUGA")
                    .startNow()
                    .build();
        }

        @Bean
        public FactoryBean<SimpleTrigger> piyoTrigger(JobDetail piyoDetail) {
            // MethodInvokingJobDetailFactoryBeanを使用しているため、JobDataは使用されません
            SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
            factoryBean.setRepeatInterval(1000);
            factoryBean.setJobDetail(piyoDetail);
            factoryBean.setJobDataAsMap(Collections.singletonMap("triggerParam", "PIYO"));
            return factoryBean;
        }

        /**
         * 引数にはJobDetailとtriggerのFactoryBeanから生成されたオブジェクトが入ってくるです。
         * afterPropertiesSetでそれぞれのインスタンス生成をしているので、
         * FactoryBeanを使う場合は各々Bean定義する必要があります。
         * ……とはいえ、FactoryBeanはXMLで書くためのものなんで、無理に使う必要ないです。
         */
        @Bean
        public SchedulerFactoryBean schedulerFactory(JobDetail hogeDetail, JobDetail fugaDetail, JobDetail piyoDetail,
                                                     ApplicationContext context) throws Exception {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setJobDetails(hogeDetail, fugaDetail, piyoDetail);
            factory.setTriggers(hogeTrigger(hogeDetail), fugaTrigger(fugaDetail), piyoTrigger(piyoDetail).getObject());
            factory.setJobFactory(new SpringBeanJobFactory() {
                @Override
                protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                    Object jobInstance = super.createJobInstance(bundle);
                    context.getAutowireCapableBeanFactory().autowireBean(jobInstance);
                    return jobInstance;
                }
            });
            return factory;
        }
    }

    public static class SamplePojoJob extends SamplePojo implements Job {

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            hello();
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
            if (latch != null) {
                logger.info("  latch count is {}.", latch.getCount());
                latch.countDown();
            }
        }
    }
}
