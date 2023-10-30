package io.spring.springbatch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    @Bean
    public Job batchjob2() {
        return jobBuilderFactory.get("batchjob2")
                .start(step2())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step22")
                .<String, String>chunk(5)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        System.out.println("ItemReader(" + i + ")");
                        return i > 3 ? null : "item" + i;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    RepeatTemplate repeatTemplate = new RepeatTemplate();
                    int i = 0;

                    @Override
                    public String process(String item) throws Exception {
                        i++;
                        System.out.println("ItemProcessor(" + i + ")");

//                        repeatTemplate.setCompletionPolicy(new TimeoutTerminationPolicy(1)); // 1ms 동안 실행
//                        repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3)); // 3번 동안 실행

                        repeatTemplate.setExceptionHandler(exceptionHandler()); // 예외 발생 시 제어

                        repeatTemplate.iterate(new RepeatCallback() {
                            @Override
                            public RepeatStatus doInIteration(RepeatContext context) throws Exception {
                                System.out.println("repeatTemplate test");
                                throw new RuntimeException("CustomException");
//                                return RepeatStatus.CONTINUABLE;
                            }
                        });

                        return item;
                    }
                })
                .writer(items -> System.out.println("items = " + items))
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new SimpleLimitExceptionHandler(3); // 예외가 발생해도 3번 실행
    }

}
