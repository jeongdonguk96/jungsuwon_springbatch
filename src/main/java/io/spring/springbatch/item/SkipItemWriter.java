package io.spring.springbatch.item;

import lombok.SneakyThrows;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class SkipItemWriter implements ItemWriter<String> {

    private int count = 0;

    @SneakyThrows
    @Override
    public void write(List<? extends String> items) throws Exception {
        count++;

        for (String item : items) {
            if (item.equals("-15")) {
                throw new RuntimeException("ItemWriter Failed, count = " + count);
            } else {
                System.out.println("ItemWriter, item = " + item);
            }
        }
    }
}
