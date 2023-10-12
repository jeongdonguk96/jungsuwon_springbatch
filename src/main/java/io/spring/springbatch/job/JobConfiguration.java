package io.spring.springbatch.job;

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
    public Job batchjob() {
        return jobBuilderFactory.get("batchjob")
                .start(step1()) // stp1을 실행한다.
                .on("FAILED") // step1의 실행결과가 실패면
                    .to(step2()) // step2를 실행한다.
                    .on("*") // step2의 실행결과과 상관없이
                        .stop() // 멈춘다.
                .from(step1()) // step1의
                .on("*") // 실행결과가 실패를 제외한 나머지라면
                    .to(step3()) // step3을 실행한다.
                    .next(step4()) // step4를 실행한다.
                    .on("FAILED") // step4의 실행결과가 실패면
                        .stop() // 멈춘다.
                .end() // FlowBuilder를 종료한다.
                .build();
//                .start(step1()) // step1을 실행한다.
//                .on("COMPLETED") // 실행결과가 성공이면
//                .to(step3()) // step3을 실행한다.
//                .from(step1())// step1의
//                .on("FAILED")// 실행결과가 실패면
//                .to(step2()) // step2을 실행한다.
//                .end() // FlowBuilder를 종료한다.
//                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("=======================");
                        System.out.println(" >> Hello Spring Batch 11111");
                        System.out.println("=======================");
                        return RepeatStatus.FINISHED;
                    }
                })
                .allowStartIfComplete(true)
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
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("=======================");
                        System.out.println(" >> Hello Spring Batch 33333");
                        System.out.println("=======================");
                        return RepeatStatus.FINISHED;
                    }
                })
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("=======================");
                        System.out.println(" >> Hello Spring Batch 44444");
                        System.out.println("=======================");
                        return RepeatStatus.FINISHED;
                    }
                })
                .allowStartIfComplete(true)
                .build();
    }

}
