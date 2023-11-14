package io.spring.springbatch.listener;

import io.spring.springbatch.domain.Customer;
import io.spring.springbatch.domain.Customer2;
import org.springframework.batch.core.ItemProcessListener;

public class CustomItemProcessListener implements ItemProcessListener<Customer, Customer2> {

    @Override
    public void beforeProcess(Customer item) {
        System.out.println(">>>>> Before Process");
    }

    @Override
    public void afterProcess(Customer item, Customer2 result) {
        System.out.println(">>>>> After Process");
    }

    @Override
    public void onProcessError(Customer item, Exception e) {
        System.out.println(">>>>> On Process Error");
    }
}