package io.spring.springbatch.job;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import io.spring.springbatch.item.CustomItemProcessor3;
import io.spring.springbatch.listener.CustomChunkListener;
import io.spring.springbatch.listener.CustomItemProcessListener;
import io.spring.springbatch.listener.CustomItemReadListener;
import io.spring.springbatch.listener.CustomItemWriteListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration11 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private final String sql5 = "INSERT INTO customer2 VALUES (:id, :firstName, :lastName, :birthDate)";

//    @Bean
    public Job batchjob11() throws Exception {
        return jobBuilderFactory.get("batchjob11")
                .incrementer(new RunIdIncrementer())
                .start(step11())
                .build();
    }

    @Bean
    public Step step11() throws Exception {
        return stepBuilderFactory.get("step11")
                .<Customer, Customer2>chunk(10)
                .listener(new CustomChunkListener())
                .reader(jdbcPagingItemReader())
                .listener(new CustomItemReadListener())
                .processor(new CustomItemProcessor3())
                .listener(new CustomItemProcessListener())
                .writer(jdbcBatchItemWriter())
                .listener(new CustomItemWriteListener())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<Customer> jdbcPagingItemReader() throws Exception {
        return new JdbcPagingItemReaderBuilder<Customer>()
                .name("jdbcPagingItemReader")
                .pageSize(10) // 사이즈 설정
                .dataSource(dataSource) // DB 설정
                .rowMapper(new BeanPropertyRowMapper<>(Customer.class)) // 매핑할 클래스 설정
                .queryProvider(pagingQueryProvider()) // PagingQueryProvider 설정
                .build();
    }

    @Bean
    public PagingQueryProvider pagingQueryProvider() throws Exception {
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("FROM customer");
        queryProvider.setSortKeys(sortKeys); // 정렬 설정

        return queryProvider.getObject();
    }

    @Bean
    public JdbcBatchItemWriter<Customer2> jdbcBatchItemWriter() {
        return new JdbcBatchItemWriterBuilder<Customer2>()
                .dataSource(dataSource)
                .sql(sql5)
                .beanMapped() // 객체와 그 필드를 DB 테이블과 컬럼에 매핑
                .build();
    }
}
