package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.CountDownLatch;

/**
 * @author irof
 */
@Configuration
@EnableBatchProcessing
public class BatchletConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BatchletConfiguration.class);

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(10);
    }

    @Bean
    public Job batchletJob(JobBuilderFactory factory, StepBuilderFactory stepBuilderFactory, CountDownLatch latch) {
        TaskletStep step = stepBuilderFactory.get("taskletStep")
                .tasklet((contribution, chunkContext) -> {
                    logger.info("XXXXXX; count={}", latch);
                    latch.countDown();
                    return RepeatStatus.continueIf(latch.getCount() > 0);
                })
                .build();
        return factory.get("batchletJob")
                .start(step)
                .build();
    }
}
