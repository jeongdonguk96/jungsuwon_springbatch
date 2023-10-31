package io.spring.springbatch.item;

import lombok.SneakyThrows;
import org.springframework.batch.item.ItemProcessor;

public class SkipItemProcessor implements ItemProcessor<String, String> {

    private int count = 0;

    @SneakyThrows
    @Override
    public String process(String item) throws Exception {
        count++;

        if (item.equals("6") || item.equals("7")) {
            throw new RuntimeException("ItemProcessor Failed, count = " + count);
        } else {
            System.out.println("ItemProcessor, item = " + item);
            return String.valueOf(Integer.parseInt(item) * -1);
        }
    }
}
