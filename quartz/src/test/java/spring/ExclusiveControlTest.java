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
            return new CountDownLatch(5);
        }

        @Bean
        public SchedulerFactoryBean scheduler() {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setJobDetails(detail1(), detail2Factory().getObject());
            factory.setTriggers(simpleTrigger("job1"), simpleTrigger("job2"));
            return factory;
        }

        private JobDetail detail1() {
            // この形式で作る場合はSlowJobはQuartzJobBeanじゃなくただのJobでも良いのだけれど、
            // latchとかをメソッドインジェクションさせたいのでこの形になる。
            // QuartzのデフォルトではPropertySettingJobFactoryが使われるのでJobでもsetterが呼ばれる。
            // しかしScheduleFactoryBeanで使われるAdaptableJobFactoryはプロパティのセットは行わない。
            // 対応はQuartzJobBeanの継承にするか、SchedulerFactoryBeanのjobFactoryを上書きするのだけど、
            // 後者の対応をしてしまうと @Scheduled などのアノテーションが使えなくなる。
            return JobBuilder.newJob(SlowJob.class)
                    .withIdentity("job1", "myJobGroup")
                    .usingJobData(new JobDataMap(Collections.singletonMap("latch", latch())))
                    .storeDurably(true)
                    .build();
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
        public MethodInvokingJobDetailFactoryBean detail2Factory() {
            SlowJob job = new SlowJob();
            job.setLatch(latch());

            // afterPropertiesSetを呼ばせるためにFactoryBeanをSpringに引き渡す
            MethodInvokingJobDetailFactoryBean factory = new MethodInvokingJobDetailFactoryBean();
            factory.setTargetObject(job);
            factory.setTargetMethod("run");
            factory.setName("job2");
            factory.setGroup("myJobGroup");

            // MethodInvokingJobDetailFactoryBeanを使用する場合はJobインスタンスを作れないので
            // @DisallowConcurrentExecutionを付与するタイミングがない。
            // (当然ではあるがtargetObjectに付けてても意味ない。）
            // なのでFactoryBeanの concurrent プロパティに設定する。
            // concurrent=false で同時実行しなくなる
            factory.setConcurrent(false);

            return factory;
        }

    }
}
