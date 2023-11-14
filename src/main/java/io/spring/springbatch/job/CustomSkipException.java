package io.spring.springbatch.job;

public class CustomSkipException extends Exception {

    public CustomSkipException() {
        super();
    }

    public CustomSkipException(String message) {
        super(message);
    }
}