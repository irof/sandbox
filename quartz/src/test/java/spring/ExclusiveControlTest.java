package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * DisallowConcurrentExecution や setConcurrent を使用した同時実行制御。
 * 同じスケジューラーインスタンス内の同じトリガーでのみ有効。
 *
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ExclusiveControlTest {

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
            return new CountDownLatch(10);
        }

        @Bean
        public SchedulerFactoryBean scheduler(JobDetail normalJobDetail, JobDetail methodInvokingJobDetail) {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setJobDetails(normalJobDetail, methodInvokingJobDetail);
            // 異なるトリガーに紐づける
            factory.setTriggers(simpleTrigger("job1"), simpleTrigger("job2"));
            // spring-bean を使用してJobのプロパティに値を突っ込む子
            factory.setJobFactory(new SpringBeanJobFactory());
            return factory;
        }

        private SimpleTrigger simpleTrigger(String jobName) {
            return TriggerBuilder.newTrigger()
                    .forJob(jobName, "myJobGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMilliseconds(500)
                            .repeatForever())
                    .build();
        }

        @Bean
        public JobDetail normalJobDetail() {
            return JobBuilder.newJob(SlowJob.class)
                    .withIdentity("job1", "myJobGroup")
                    .usingJobData(new JobDataMap(Collections.singletonMap("latch", latch())))
                    .storeDurably(true)
                    .build();
        }

        @Bean
        public MethodInvokingJobDetailFactoryBean methodInvokingJobDetail() {
            SlowJob job = new SlowJob();
            job.setLatch(latch());

            // afterPropertiesSetを呼ばせるためにFactoryBeanをSpringに引き渡す
            MethodInvokingJobDetailFactoryBean factory = new MethodInvokingJobDetailFactoryBean();
            factory.setTargetObject(job);
            factory.setTargetMethod("run");
            factory.setName("job2");
            factory.setGroup("myJobGroup");

            // MethodInvokingJobDetailFactoryBeanを使用する場合、
            // たとえtargetObjectがJobのインスタンスで@DisallowConcurrentExecutionが付与されていても、
            // Quartzが認識するのはFactoryが生成するJobDetailImplのため、排他制御は行われない。
            // なのでFactoryBeanの concurrent プロパティに false を設定する。
            factory.setConcurrent(false);

            return factory;
        }

    }

    @DisallowConcurrentExecution
    public static class SlowJob implements Job {

        private static Logger logger = LoggerFactory.getLogger("spring");

        private CountDownLatch latch;
        private long sleeps = 5;

        public void setLatch(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            run();
        }

        private void run() throws JobExecutionException {
            latch.countDown();
            logger.info("executing {}... sleep: {} seconds, latch: {}", this, sleeps, latch.getCount());

            try {
                TimeUnit.SECONDS.sleep(sleeps);
            } catch (InterruptedException e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
