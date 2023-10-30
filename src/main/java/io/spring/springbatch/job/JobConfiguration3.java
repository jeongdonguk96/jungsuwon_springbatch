package io.spring.springbatch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration3 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchjob3() {
        return jobBuilderFactory.get("batchjob3")
                .start(step3())
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .<String, String>chunk(5)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        if (i == 1) {
                            throw new IllegalArgumentException("CustomSkipException");
                        }

                        return i > 3 ? null : "item " + i;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    int i = 0;

                    @Override
                    public String process(String item) throws Exception {
                        i++;
                        System.out.println("ItemProcessor(" + i + ")");

                        throw new IllegalStateException("CustomRetryException");
                    }
                })
                .writer(items -> System.out.println("items = " + items))
                .faultTolerant() // 장애 처리 설정
                .skip(IllegalAccessException.class)
                .skipLimit(2)
                .retry(IllegalStateException.class)
                .retryLimit(2)
                .allowStartIfComplete(true)
                .build();
    }

}
