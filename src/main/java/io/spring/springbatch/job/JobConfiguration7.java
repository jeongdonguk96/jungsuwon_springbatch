package io.spring.springbatch.job;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import io.spring.springbatch.item.CustomItemProcessor3;
import io.spring.springbatch.listener.CustomItemProcessListener;
import io.spring.springbatch.listener.CustomItemReadListener;
import io.spring.springbatch.listener.CustomItemWriteListener;
import io.spring.springbatch.listener.StopWatchJobListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration7 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchjob7() throws Exception {
        return jobBuilderFactory.get("batchjob7")
                .start(flow1())
                .split()
                .listener(new StopWatchJobListener())
                .build();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<Flow>("flow")
                .start(stet1)
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .tasklet(tasklet())
                .build();
    }
}
