package io.spring.springbatch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration4 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchjob4() {
        return jobBuilderFactory.get("batchjob4")
                .start(step4())
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step44")
                .<String, String>chunk(5)
                .reader(listItemReader())
                .processor(processor())
                .writer(items -> items.forEach(item -> System.out.println("item = " + item)))
                .faultTolerant()
                .skip(RuntimeException.class)
                .skipLimit(2)
                .retry(RuntimeException.class)
                .retryLimit(2)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ListItemReader<String> listItemReader() {
        List<String> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemProcessor<? super String, String> processor() {
        return new RetryItemProcessor();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        Map<Class<? extends Throwable>, Boolean> excepttionClass = new HashMap<>();
        excepttionClass.put(RuntimeException.class, true);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2, excepttionClass);
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000);

        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        return retryTemplate;
    }
}
