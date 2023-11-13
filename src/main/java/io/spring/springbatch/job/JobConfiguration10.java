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
public class JobConfiguration10 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    @Bean
    public Job batchjob10() {
        return jobBuilderFactory.get("batchjob10")
                .incrementer(new RunIdIncrementer())
                .start(step10())
                .next(step1011())
//                .listener(new CustomJobExecutionListener())
                .listener(new CustomAnnotationJobExecutionListener())
                .build();
    }

    @Bean
    public Step step10() {
        return stepBuilderFactory.get("step101")
                .tasklet(((contribution, chunkContext) -> RepeatStatus.FINISHED))
                .listener(new CustomStepExecutionListener())
//                .listener(new CustomAnnotationJobExecutionListener())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step1011() {
        return stepBuilderFactory.get("step10111")
                .tasklet(((contribution, chunkContext) -> RepeatStatus.FINISHED))
                .listener(new CustomStepExecutionListener())
//                .listener(new CustomAnnotationJobExecutionListener())
                .allowStartIfComplete(true)
                .build();
    }
}
