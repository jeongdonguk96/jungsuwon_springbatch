package io.spring.springbatch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class CustomTasklet implements Tasklet {

    private long sum;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        for (int i = 0; i < 1000000000; i++) {
            sum++;
        }
        System.out.println("%s has been executed on thread %s", chunkContext.getStepContext().getStepName(), chunkContext);

        return null;
    }
}
