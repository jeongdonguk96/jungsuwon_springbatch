package io.spring.springbatch.job;

import io.spring.springbatch.flatfileitemreader.CustomLineMapper;
import io.spring.springbatch.flatfileitemreader.CustomerFieldSetMapper;
import io.spring.springbatch.item.CustomItemProcessor;
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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchjob() {
        return jobBuilderFactory.get("batchjob")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public ItemReader DelimetedTokenizeritemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFileReader")
                .resource(new ClassPathResource("customer.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("name", "year", "age")
                .build();
    }

    @Bean
    public ItemReader<Customer> itemReader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>(); // 플랫파일아이템리더 인스턴스 생성
        itemReader.setResource(new ClassPathResource("/customer.csv")); // 읽어들일 파일 설정

        CustomLineMapper<Customer> lineMapper = new CustomLineMapper<>(); // 라인매퍼 인스턴스 생성
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer()); // 이용할 구분자 객체 설정
        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper()); // 필드셋 객체 설정

        itemReader.setLineMapper(lineMapper); // 플랫파일아이템리더의 라인매퍼 설정
        itemReader.setLinesToSkip(1); // 첫 번째 라인(컬럼명) 건너뛰기

        return itemReader;
    }

    @Bean
    public ItemProcessor<Customer, Customer> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return new CustomItemWriter();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(2)
                .reader(DelimetedTokenizeritemReader())
                .writer(itemWriter())
//                .allowStartIfComplete(true)
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
