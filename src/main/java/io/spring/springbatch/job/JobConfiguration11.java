package io.spring.springbatch.job;

import io.spring.springbatch.listener.CustomAnnotationJobExecutionListener;
import io.spring.springbatch.listener.CustomStepExecutionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration11 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchjob11() {
        return jobBuilderFactory.get("batchjob11")
                .incrementer(new RunIdIncrementer())

                .build();
    }

    @Bean
    public Step step11() {
        return stepBuilderFactory.get("step11")
                .tasklet(((contribution, chunkContext) -> RepeatStatus.FINISHED))
                .listener(new CustomStepExecutionListener())
                .allowStartIfComplete(true)
                .build();
    }
}
