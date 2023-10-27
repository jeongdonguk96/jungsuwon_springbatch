package io.spring.springbatch.job;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import io.spring.springbatch.item.CustomItemProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    private final String sql1 = "SELECT * FROM customer WHERE firstName like ? ORDER BY lastName, firstName";
    private final String sql2 = "SELECT * FROM customer ORDER BY lastName, firstName";
    private final String arg = "A%";
    private final String sql3 = "SELECT c FROM Customer c WHERE firstName like :firstName ORDER BY lastName, firstName";
    private final String sql4 = "SELECT c FROM Customer c";
    private final String sql5 = "INSERT INTO customer2 VALUES (:id, :firstName, :lastName, :birthDate)";


    @Bean
    public Job batchjob() {
        return jobBuilderFactory.get("batchjob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer2>chunk(10)
                .reader(jpaCursorItemReader())
                .processor(compositeItemProcessor())
                .writer(jpaItemWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<Customer> jpaCursorItemReader() {
//        HashMap<String, Object> parameters = new HashMap<>();
//        parameters.put("firstName", "B%");

        return new JpaCursorItemReaderBuilder<Customer>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory) // EntityManager 설정
                .queryString(sql4) // 실행할 jpql문
//                .parameterValues(parameters) // jpql문 내 인자
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer2> compositeItemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<? super Customer2> jpaItemWriter() {
        return new JpaItemWriterBuilder<>()
                .usePersist(true) // 영속화할 것인지 여부
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

//    @Bean
//    public ItemProcessor<Customer, Customer2> customItemProcessor() {
//        return new CustomItemProcessor();
//    }

//    @Bean
//    public ItemWriter<? super Customer2> jdcbBatchItemWriter() {
//        return new JdbcBatchItemWriterBuilder<>()
//                .dataSource(dataSource)
//                .sql(sql5)
//                .beanMapped() // 객체와 그 필드를 DB 테이블과 컬럼에 매핑
//                .build();
//    }

//    @Bean
//    public ItemWriter<? super Customer> formattedFlatFileItemWriter() {
//        return new FlatFileItemWriterBuilder<>()
//                .name("formattedFlatFileItemWriter")
//                .resource(new FileSystemResource("src/main/resources/customer_writer.txt")) // 파일 경로 설정
//                .formatted().format("%-1d%-5s%-10s%-6s")
//                .names("id", "firstName", "lastName", "birthDate")
//                .build();
//    }

//    @Bean
//    public ItemWriter<? super Customer> delimitedFlatFileItemWriter() {
//        return new FlatFileItemWriterBuilder<>()
//                .name("delimitedFlatFileItemWriter")
//                .resource(new FileSystemResource("src/main/resources/customer_writer.txt")) // 파일 경로 설정
////                .append(true) // 이어 쓰기 설정
//                .shouldDeleteIfEmpty(true) // Reader에서 아무런 파일이 오지 않으면 위 리소스의 FlatFile 삭제
////                .shouldDeleteIfExists(false) // 기존 동일한 파일이 있어도 삭제 X
//                .delimited().delimiter(",")
//                .names("id", "firstName", "lastName", "birthDate")
//                .build();
//    }

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
//    public ItemReader<Customer> jpaPagingItemReader() {
//        HashMap<String, Object> parameters = new HashMap<>();
//        parameters.put("firstName", "B%");
//
//        return new JpaPagingItemReaderBuilder<Customer>()
//                .name("jpaCursorItemReader")
//                .pageSize(1) // 가져올 데이터 갯수
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
//    public ItemWriter<Customer> itemWriter() {
//        return new CustomItemWriter();
//    }
}
