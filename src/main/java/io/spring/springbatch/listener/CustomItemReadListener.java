package io.spring.springbatch.listener;

import io.spring.springbatch.domain.Customer;
import org.springframework.batch.core.ItemReadListener;

public class CustomItemReadListener implements ItemReadListener<Customer> {

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(Customer item) {
        System.out.println("Thread : " + Thread.currentThread().getName() + ", read item : " + item.getId());
    }

    @Override
    public void onReadError(Exception ex) {

    }
}
