package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;

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
            // この形式で作る場合はSlowJobはQuartzJobBeanじゃなくただのJobでも良いのだけれど、
            // setter経由でQuartzに何かしら設定させたいならこういう形。

            // QuartzのデフォルトではPropertySettingJobFactoryが使われるのでJobでもsetterが呼ばれるのだけれど、
            // しかしScheduleFactoryBeanで使われるAdaptableJobFactoryはプロパティのセットが行われない。

            // 対応はQuartzJobBeanの継承にするか、SchedulerFactoryBeanのjobFactoryを上書きするのだけど、
            // 後者の対応をしてしまうと @Scheduled などのアノテーションが使えなくなる。
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
}
