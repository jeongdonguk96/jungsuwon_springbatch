package io.spring.springbatch.item;

import io.spring.springbatch.domain.Customer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

public class CustomItemReader implements ItemReader<Customer> {

    private List<Customer> customerList;

    public CustomItemReader(List<Customer> customerList) {
        this.customerList = new ArrayList<>(customerList);
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!customerList.isEmpty()) {
            System.out.println("회차 시작");
            return customerList.remove(0);
        }

        return null;
    }
}
