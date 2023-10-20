package io.spring.springbatch.flatfileitemreader;

import io.spring.springbatch.item.Customer;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

    @Override
    public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
        if (fieldSet == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setName(fieldSet.readString(0));
        customer.setYear(fieldSet.readString(1));
        customer.setAge(fieldSet.readInt(2));

        return customer;
    }
}
