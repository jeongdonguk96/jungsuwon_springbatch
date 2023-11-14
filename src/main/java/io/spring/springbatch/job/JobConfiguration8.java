package io.spring.springbatch.job;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import io.spring.springbatch.partitioner.ColumnRangePartitioner;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration8 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;


    private final String sql5 = "INSERT INTO customer2 VALUES (:id, :firstName, :lastName, :birthDate)";

//    @Bean
    public Job batchjob8() {
        return jobBuilderFactory.get("batchjob8")
                .incrementer(new RunIdIncrementer())
                .start(masterStep1())
                .build();
    }

//    @Bean
    public Step masterStep1() {
        return stepBuilderFactory.get("masterStep1")
                .partitioner(slaveStep1().getName(), partitioner())
                .step(slaveStep1())
                .gridSize(4) // 4개의 Slave Step 설정
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

//    @Bean
    public Step slaveStep1() {
        return stepBuilderFactory.get("slaveStep1")
                .<Customer, Customer2>chunk(100)
                .reader(jdbcPagingItemReader(null, null))
                .writer(jdcbBatchItemWriter())
                .allowStartIfComplete(true)
                .build();
    }

//    @Bean
    public Partitioner partitioner() {
        ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
        partitioner.setDataSource(dataSource);
        partitioner.setTable("customer");
        partitioner.setColumn("id");

        return partitioner;
    }

//    @Bean
//    @StepScope
    public ItemReader<Customer> jdbcPagingItemReader(
            @Value("#{stepExecutionContext['minValue']}") Long minValue,
            @Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
        System.out.println("reading : " + minValue + ", to : " + maxValue);

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("FROM customer");
        queryProvider.setWhereClause("WHERE id >= " + minValue + " AND id <= " + maxValue);
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        return new JdbcPagingItemReaderBuilder<Customer>()
                .name("jdbcPagingItemReader")
                .pageSize(100) // 사이즈 설정
                .dataSource(dataSource) // DB 설정
                .queryProvider(queryProvider) // PagingQueryProvider 설정
                .rowMapper(new BeanPropertyRowMapper<>(Customer.class)) // 매핑할 클래스 설정
                .build();
    }

//    @Bean
//    @StepScope
    public ItemWriter<? super Customer2> jdcbBatchItemWriter() {
        return new JdbcBatchItemWriterBuilder<>()
                .dataSource(dataSource)
                .sql(sql5)
                .beanMapped() // 객체와 그 필드를 DB 테이블과 컬럼에 매핑
                .build();
    }
}
