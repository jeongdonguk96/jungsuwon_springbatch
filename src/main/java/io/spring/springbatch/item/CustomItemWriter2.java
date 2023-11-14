package io.spring.springbatch.item;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomItemWriter2 implements ItemWriter<String> {

    int count = 0;

    @Override
    public void write(List<? extends String> items) throws CustomRetryException {
        for (String item : items) {
            if (count < 2) {
                if (count % 2 == 0) {
                    count = count + 1;

                } else if (count % 2 == 1) {
                    count = count + 1;
                    throw new CustomRetryException("failed");
                }
            }
            System.out.println("write : " + item);
        }
    }
}
