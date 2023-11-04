//package io.spring.springbatch.job;
//
//import io.spring.springbatch.domain.Customer;
//import io.spring.springbatch.domain.Customer2;
//import io.spring.springbatch.listener.StopWatchJobListener;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.integration.async.AsyncItemProcessor;
//import org.springframework.batch.integration.async.AsyncItemWriter;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.PagingQueryProvider;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
//import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//public class JobConfiguration5 {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final DataSource dataSource;
//
//    private final String sql5 = "INSERT INTO customer2 VALUES (:id, :firstName, :lastName, :birthDate)";
//
////    @Bean
//    public Job batchjob5() throws Exception {
//        return jobBuilderFactory.get("batchjob5")
//                .start(step5())
//                .listener(new StopWatchJobListener())
//                .build();
//    }
//
//    @Bean
//    public Step step5() throws Exception {
//        return stepBuilderFactory.get("step5")
//                .<Customer, Customer2>chunk(100)
//                .reader(jdbcPagingItemReader())
//                .processor(customItemProcessor())
//                .writer(jdbcBatchItemWriter())
//                .listener(new StopWatchJobListener())
//                .build();
//    }
//
//    @Bean
//    public Step asyncStep5() throws Exception {
//        return stepBuilderFactory.get("asyncStep5")
//                .<Customer, Customer2>chunk(100)
//                .reader(jdbcPagingItemReader())
////                .processor(asyncItemProcessor())
////                .writer(asyncItemWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Customer> jdbcPagingItemReader() throws Exception {
//        return new JdbcPagingItemReaderBuilder<Customer>()
//                .name("jdbcPagingItemReader")
//                .pageSize(300) // 사이즈 설정
//                .dataSource(dataSource) // DB 설정
//                .rowMapper(new BeanPropertyRowMapper<>(Customer.class)) // 매핑할 클래스 설정
//                .queryProvider(pagingQueryProvider()) // PagingQueryProvider 설정
//                .build();
//    }
//
//    @Bean
//    public PagingQueryProvider pagingQueryProvider() throws Exception {
//        Map<String, Order> sortKeys = new HashMap<>();
//        sortKeys.put("id", Order.ASCENDING);
//
//        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
//        queryProvider.setDataSource(dataSource);
//        queryProvider.setSelectClause("*");
//        queryProvider.setFromClause("FROM customer");
//        queryProvider.setSortKeys(sortKeys); // 정렬 설정
//
//        return queryProvider.getObject();
//    }
//
//    @Bean
//    public ItemProcessor<Customer, Customer2> customItemProcessor() throws InterruptedException {
//        return new ItemProcessor<Customer, Customer2>() {
//            @Override
//            public Customer2 process(Customer item) throws Exception {
//                Thread.sleep(30);
//                return new Customer2(item.getId(), item.getFirstName().toUpperCase(), item.getLastName().toUpperCase(), item.getBirthDate());
//            }
//        };
//    }
//
//    @Bean
//    public AsyncItemProcessor<Customer, Customer2> asyncItemProcessor() throws InterruptedException {
//        AsyncItemProcessor<Customer, Customer2> asyncProcessor = new AsyncItemProcessor<>();
//        asyncProcessor.setDelegate(customItemProcessor());
//        asyncProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
//
//        return asyncProcessor;
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<Customer2> jdbcBatchItemWriter() {
//        return new JdbcBatchItemWriterBuilder<Customer2>()
//                .dataSource(dataSource)
//                .sql(sql5)
//                .beanMapped() // 객체와 그 필드를 DB 테이블과 컬럼에 매핑
//                .build();
//    }
//
//    @Bean
//    public AsyncItemWriter<Customer2> asyncItemWriter() {
//        AsyncItemWriter<Customer2> asyncWriter = new AsyncItemWriter<>();
//        asyncWriter.setDelegate(jdbcBatchItemWriter());
//
//        return asyncWriter;
//    }
//}
