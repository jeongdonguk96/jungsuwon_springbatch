package io.spring.springbatch.job;

import io.spring.springbatch.item.CustomItemProcessor5;
import io.spring.springbatch.item.CustomItemWriter2;
import io.spring.springbatch.item.CustomRetryException;
import io.spring.springbatch.item.LinkedListItemReader;
import io.spring.springbatch.listener.CustomRetryListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration12 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchjob12() throws Exception {
        return jobBuilderFactory.get("batchjob12")
                .incrementer(new RunIdIncrementer())
                .start(step12())
                .build();
    }

    @Bean
    public Step step12() throws Exception {
        return stepBuilderFactory.get("step12")
                .<Integer, String>chunk(10)
                .reader(linkedListItemReader2())
                .processor(new CustomItemProcessor5())
                .writer(new CustomItemWriter2())
                .faultTolerant()
                .retry(CustomRetryException.class)
                .retryLimit(2)
                .listener(new CustomRetryListener())
                .build();
    }

    @Bean
    public ItemReader<Integer> linkedListItemReader() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        return new LinkedListItemReader<>(list);
    }

    @Bean
    public ItemReader<Integer> linkedListItemReader2() {
        List<Integer> list = Arrays.asList(1,2,3,4);
        return new LinkedListItemReader<>(list);
    }
}
