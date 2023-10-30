package io.spring.springbatch.item;

import io.spring.springbatch.domain.Customer2;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<Customer2, Customer2> {

    @Override
    public Customer2 process(Customer2 item) throws Exception {

        item.setFirstName(item.getFirstName() + "입니다.");
        item.setLastName(item.getLastName() + "입니다.");
        item.setBirthDate(item.getBirthDate() + "입니다.");

        return item;
    }
}
