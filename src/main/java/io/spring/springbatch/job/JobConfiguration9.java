package io.spring.springbatch.job;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import io.spring.springbatch.listener.CustomItemReadListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration9 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private final String sql2 = "SELECT * FROM customer";
    private final String sql5 = "INSERT INTO customer2 VALUES (:id, :firstName, :lastName, :birthDate)";


//    @Bean
    public Job batchjob9() {
        return jobBuilderFactory.get("batchjob9")
                .start(step9())
                .build();
    }

    @Bean
    public Step step9() {
        return stepBuilderFactory.get("step9")
                .<Customer, Customer2>chunk(10)
                .reader(jdbcCursorItemReader9())
                .listener(new CustomItemReadListener())
                .writer(jdbcBatchItemWriter9())
                .allowStartIfComplete(true)
                .taskExecutor(taskExecutor9())
                .build();
    }

    @Bean
    public ItemWriter<? super Customer2> jdbcBatchItemWriter9() {
        return new JdbcBatchItemWriterBuilder<>()
                .dataSource(dataSource)
                .sql(sql5)
                .beanMapped() // 객체와 그 필드를 DB 테이블과 컬럼에 매핑
                .build();
    }

    @Bean
    public SynchronizedItemStreamReader<Customer> jdbcCursorItemReader9() {
        JdbcCursorItemReader<Customer> jdbcCursorItemReader = new JdbcCursorItemReaderBuilder<Customer>()
                .name("jdbcCursorItemReader9")
                .fetchSize(10) // 한 번에 가져올 데이터 수
                .sql(sql2) // 실행할 sql문
                .beanRowMapper(Customer.class) // 변환할 클래스
                .dataSource(dataSource) // DB 설정
                .build();

        return new SynchronizedItemStreamReaderBuilder<Customer>()
                .delegate(jdbcCursorItemReader)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor9() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setThreadNamePrefix("Thread-Safe");

        return taskExecutor;
    }
}
