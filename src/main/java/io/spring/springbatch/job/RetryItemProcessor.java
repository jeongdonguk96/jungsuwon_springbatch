package io.spring.springbatch.job;

import org.springframework.batch.item.ItemProcessor;

public class RetryItemProcessor implements ItemProcessor<String, String> {

    private int count = 0;

    @Override
    public String process(String item) throws Exception {
        if (item.equals("2") || item.equals("3")) {
            count++;
            throw new RuntimeException("failed count = " + count);
        }

        return item;
    }
}
