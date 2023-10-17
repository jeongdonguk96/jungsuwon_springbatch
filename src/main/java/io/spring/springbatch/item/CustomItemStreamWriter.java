package io.spring.springbatch.item;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

import java.util.List;

public class CustomItemStreamWriter implements ItemStreamWriter {

    @Override
    public void write(List items) throws Exception {
        items.forEach(item -> System.out.println("item = " + item));
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("open() executed!!!");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("update() executed!!!");
    }

    @Override
    public void close() throws ItemStreamException {
        System.out.println("close() executed!!!");
    }
}
