package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchjob3() {
        return jobBuilderFactory.get("batchjob3")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Job batchjob4() {
        return jobBuilderFactory.get("batchjob4")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                        JobParameters jobParameters = stepContribution.getStepExecution().getJobExecution().getJobParameters();
//                        String name = jobParameters.getString("name");
//                        Long seq = jobParameters.getLong("seq");
//                        Double age = jobParameters.getDouble("age");
//                        Date date = jobParameters.getDate("date");
                        System.out.println("=======================");
                        System.out.println(" >> Hello Spring Batch 11111");
//                        System.out.println("name = " + name);
//                        System.out.println("seq = " + seq);
//                        System.out.println("age = " + age);
//                        System.out.println("date = " + date);
                        System.out.println("=======================");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("=======================");
                        System.out.println(" >> Hello Spring Batch 22222");
                        System.out.println("=======================");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}