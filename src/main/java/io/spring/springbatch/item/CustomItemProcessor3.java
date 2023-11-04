package io.spring.springbatch.item;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor3 implements ItemProcessor<Customer, Customer2> {

    @Override
    public Customer2 process(Customer item) throws Exception {

        Customer2 customer2 = new Customer2();
        customer2.setId(item.getId());
        customer2.setFirstName(item.getFirstName());
        customer2.setLastName(item.getFirstName());
        customer2.setBirthDate(item.getBirthDate());

        return customer2;
    }
}
