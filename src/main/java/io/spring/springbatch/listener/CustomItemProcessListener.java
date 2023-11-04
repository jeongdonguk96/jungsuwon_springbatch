package io.spring.springbatch.listener;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import org.springframework.batch.core.ItemProcessListener;

public class CustomItemProcessListener implements ItemProcessListener<Customer, Customer2> {

    @Override
    public void beforeProcess(Customer item) {

    }

    @Override
    public void afterProcess(Customer item, Customer2 result) {
        System.out.println("Thread : " + Thread.currentThread().getName() + ", process item : " + item.getId());
    }

    @Override
    public void onProcessError(Customer item, Exception e) {

    }
}
