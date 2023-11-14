package io.spring.springbatch.simplejob;

import io.spring.springbatch.job.JobConfiguration11;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// 1개의 Job에 대해서만 테스트 코드를 작성할 수 있다.
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobConfiguration11.class)
@SpringBatchTest
@EnableBatchProcessing
@EnableAutoConfiguration
public class SimpleJobTest {

    @Autowired private JobLauncherTestUtils launcherTestUtils;
    @Autowired private JdbcTemplate jdbcTemplate;

    @Test
    public void simpleJob_test() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("date", new Date().getTime())
                .toJobParameters();

        // when
        JobExecution jobExecution = launcherTestUtils.launchJob(jobParameters);
        JobExecution jobExecution1 = launcherTestUtils.launchStep("step11");

        // then
        assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);

        StepExecution stepExecution = (StepExecution) ((List) jobExecution1.getStepExecutions()).get(0);

        assertEquals(stepExecution.getCommitCount(), 2);
        assertEquals(stepExecution.getReadCount(), 100);
        assertEquals(stepExecution.getWriteCount(), 100);
    }

    @After
    public void clear() {
        jdbcTemplate.execute("DELETE FROM Customer2");
    }
}
