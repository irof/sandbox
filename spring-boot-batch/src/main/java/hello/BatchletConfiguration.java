package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author irof
 */
@Configuration
@EnableBatchProcessing
public class BatchletConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BatchletConfiguration.class);

    @Bean
    public Job batchletJob(JobBuilderFactory factory, StepBuilderFactory stepBuilderFactory) {
        TaskletStep step = stepBuilderFactory.get("taskletStep")
                .tasklet((contribution, chunkContext) -> {
                    logger.info("XXXXXX");
                    return null;
                })
                .build();
        return factory.get("batchletJob")
                .start(step)
                .build();
    }
}
