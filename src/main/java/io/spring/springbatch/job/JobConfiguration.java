package io.spring.springbatch.job;

import io.spring.springbatch.item.CustomItemWriter;
import io.spring.springbatch.item.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final String sql3 = "SELECT c FROM Customer c WHERE firstName like :firstName ORDER BY lastName, firstName";
    private final String sql4 = "SELECT c FROM Customer c";


    private final DataSource dataSource;
    private final String sql1 = "SELECT * FROM customer WHERE firstName like ? ORDER BY lastName, firstName";
    private final String sql2 = "SELECT * FROM customer ORDER BY lastName, firstName";
    private final String arg = "A%";

    @Bean
    public Job batchjob() {
        return jobBuilderFactory.get("batchjob")
                .start(step1())
                .build();
    }

    @Bean
    public ItemReader<Customer> jpaPagingItemReader() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("firstName", "A%");

        return new JpaPagingItemReaderBuilder<Customer>()
                .name("jpaCursorItemReader")
                .pageSize(10) // 가져올 데이터 갯수
                .entityManagerFactory(entityManagerFactory) // EntityManager 설정
                .queryString(sql3) // 실행할 jpql문
                .parameterValues(parameters) // jpql문 내 인자
                .build();
    }

//    @Bean
//    public ItemReader<Customer> jdbcPagingItemReader() throws Exception {
//        return new JdbcPagingItemReaderBuilder<Customer>()
//                .name("jdcbPagingItemReader")
//                .pageSize(10) // 사이즈 설정
//                .dataSource(dataSource) // DB 설정
//                .rowMapper(new BeanPropertyRowMapper<>(Customer.class)) // 매핑할 클래스 설정
//                .queryProvider(pagingQueryProvider()) // PagingQueryProvider 설정
//                .build();
//    }

//    @Bean
//    public PagingQueryProvider pagingQueryProvider() throws Exception {
//        Map<String, Order> sortKeys = new HashMap<>();
//        sortKeys.put("id", Order.ASCENDING);
//
//        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
//        queryProvider.setDataSource(dataSource);
//        queryProvider.setSelectClause("*");
//        queryProvider.setFromClause("FREOM customer");
//        queryProvider.setWhereClause("WHERE firstName like :firstName");
//        queryProvider.setSortKeys(sortKeys); // 정렬 설정
//
//        return queryProvider.getObject();
//    }

//    @Bean
//    public ItemReader<Customer> jpaCursorItemReader() {
//        HashMap<String, Object> parameters = new HashMap<>();
//        parameters.put("firstName", "A%");
//
//        return new JpaCursorItemReaderBuilder<Customer>()
//                .name("jpaCursorItemReader")
//                .entityManagerFactory(entityManagerFactory) // EntityManager 설정
//                .queryString(sql3) // 실행할 jpql문
//                .parameterValues(parameters) // jpql문 내 인자
//                .build();
//    }

//    @Bean
//    public ItemReader<Customer> jdcbCursorItemReader() {
//        return new JdbcCursorItemReaderBuilder<Customer>()
//                .name("jdcbCursorItemReader")
//                .fetchSize(10) // 한 번에 가져올 데이터 수
//                .sql(sql1) // 실행할 sql문
//                .beanRowMapper(Customer.class) // 변환할 클래스
//                .queryArguments(arg) // sql문 내 인자
//                .dataSource(dataSource) // DB 설정
//                .build();
//    }

//    @Bean
//    public ItemReader FixedLengthTokenizeritemReader() {
//        return new FlatFileItemReaderBuilder<Customer>()
//                .name("flatFileReader")
//                .resource(new ClassPathResource("customer.txt"))
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<>()) // 배치에서 제공하는 클래스 사용
//                .targetType(Customer.class) // 변환될 타입
//                .linesToSkip(1)
//                .fixedLength()
//                .strict(false) // 검증 회피
//                .addColumns(new Range(1, 5)) // 길이 지정
//                .addColumns(new Range(6, 9)) // 길이 지정
//                .addColumns(new Range(10, 11)) // 길이 지정
//                .names("name", "year", "age") // 컬럼명 명시
//                .build();
//    }

//    @Bean
//    public ItemReader DelimetedTokenizeritemReader() {
//        return new FlatFileItemReaderBuilder<Customer>()
//                .name("flatFileReader")
//                .resource(new ClassPathResource("customer.csv"))
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<>()) // 배치에서 제공하는 클래스 사용
//                .targetType(Customer.class) // 변환될 타입
//                .linesToSkip(1)
//                .delimited().delimiter(",") // 구분자로 식별 후 읽어들임
//                .names("name", "year", "age")
//                .build();
//    }

//    @Bean
//    public ItemReader<Customer> itemReader() {
//        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>(); // 플랫파일아이템리더 인스턴스 생성
//        itemReader.setResource(new ClassPathResource("/customer.csv")); // 읽어들일 파일 설정
//
//        CustomLineMapper<Customer> lineMapper = new CustomLineMapper<>(); // 라인매퍼 인스턴스 생성
//        lineMapper.setLineTokenizer(new DelimitedLineTokenizer()); // 이용할 구분자 객체 설정
//        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper()); // 필드셋 객체 설정
//
//        itemReader.setLineMapper(lineMapper); // 플랫파일아이템리더의 라인매퍼 설정
//        itemReader.setLinesToSkip(1); // 첫 번째 라인(컬럼명) 건너뛰기
//
//        return itemReader;
//    }

//    @Bean
//    public ItemProcessor<Customer, Customer> itemProcessor() {
//        return new CustomItemProcessor();
//    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return new CustomItemWriter();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(10)
                .reader(jpaPagingItemReader())
                .writer(itemWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step2 done");
                        return RepeatStatus.FINISHED;
                    }
                })
                .allowStartIfComplete(true)
                .build();
    }
}
