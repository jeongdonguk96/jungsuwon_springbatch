package io.spring.springbatch.listener;

import io.spring.springbatch.domain.Customer;
import org.springframework.batch.core.ItemReadListener;

public class CustomItemReadListener implements ItemReadListener<Customer> {

    @Override
    public void beforeRead() {
        System.out.println(">>>>> Before Read");
    }

    @Override
    public void afterRead(Customer item) {
        System.out.println(">>>>> After Read");
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println(">>>>> After Read Error");
    }
}
