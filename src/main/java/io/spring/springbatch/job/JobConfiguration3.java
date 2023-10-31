package io.spring.springbatch.job;

import io.spring.springbatch.item.SkipItemProcessor;
import io.spring.springbatch.item.SkipItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
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
        return stepBuilderFactory.get("step33")
                .<String, String>chunk(5)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        System.out.println("index = " + i);
                        return i > 20 ? null : String.valueOf(i);
                    }
                })
                .processor(new SkipItemProcessor())
                .writer(new SkipItemWriter())
                .faultTolerant() // 장애 처리 설정
                .skip(RuntimeException.class) // 건너뛸 예외 지정
                .skipLimit(3) //건너뛸 횟수 지정
//                .skipPolicy(new LimitCheckingItemSkipPolicy()) // 스킵 정책 설정
//                .noSkip(IllegalAccessException.class) // 해당 예외는 건너뛰지 않음
                .allowStartIfComplete(true)
                .build();
    }

}
