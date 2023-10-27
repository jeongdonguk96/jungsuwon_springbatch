package io.spring.springbatch.item;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer2> {

    ClassifierCompositeItemProcessor<Customer, Customer2> itemProcessor = new ClassifierCompositeItemProcessor<>();

    @Override
    public Customer2 process(Customer item) throws Exception {
        return null;
    }

//    @Override
//    public Customer2 process(Customer item) throws Exception {
//        count++;
//
//        Customer2 customer2 = new Customer2();
//        customer2.setId(item.getId());
//        customer2.setFirstName(item.getFirstName() + " " + count);
//        customer2.setLastName(item.getLastName() + " " + count);
//        customer2.setBirthDate(item.getBirthDate() + " " + count);
//
//        return customer2;
//    }

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
