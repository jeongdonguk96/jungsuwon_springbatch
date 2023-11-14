package io.spring.springbatch.item;

import io.spring.springbatch.job.CustomSkipException;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor4 implements ItemProcessor<Integer, String> {
    @Override
    public String process(Integer item) throws Exception{
        if (item == 4) {
            throw new CustomSkipException("process skipped");
        }
//        System.out.println("process : " + item);
        return "item" + item;
    }
}
