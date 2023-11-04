package io.spring.springbatch.listener;

import io.spring.springbatch.domain.Customer2;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class CustomItemWriteListener implements ItemWriteListener<Customer2> {

    @Override
    public void beforeWrite(List<? extends Customer2> items) {

    }

    @Override
    public void afterWrite(List<? extends Customer2> items) {
        System.out.println("Thread : " + Thread.currentThread().getName() + ", write item size : " + items.size());
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Customer2> items) {

    }
}
