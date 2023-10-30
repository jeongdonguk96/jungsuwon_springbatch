package io.spring.springbatch.item;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer2> {

    @Override
    public Customer2 process(Customer item) {

        if (item.getId() % 2 == 0L) {
            Customer2 customer2 = new Customer2();
            customer2.setId(item.getId());
            customer2.setFirstName(item.getFirstName());
            customer2.setLastName(item.getLastName());
            customer2.setBirthDate(item.getBirthDate());
            return customer2;
        }

        return null;
    }
//    @Override
//    public Customer2 process(Customer item) throws Exception {
//        Customer2 customer2 = new Customer2();
//        customer2.setId(item.getId());
//        customer2.setFirstName(item.getFirstName() + "_2");
//        customer2.setLastName(item.getLastName() + "_2");
//        customer2.setBirthDate(item.getBirthDate() + "_2");
//
//        return customer2;
//    }
}
