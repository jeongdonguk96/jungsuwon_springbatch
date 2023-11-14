package io.spring.springbatch.item;

public class CustomRetryException extends Exception {
    public CustomRetryException(String message) {
        super(message);
    }
}
