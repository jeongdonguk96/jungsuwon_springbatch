package io.spring.springbatch.listener;

import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener {

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println(">> onSkipInRead : "+ t.getMessage());
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
        System.out.println(">> onSkipInWrite : "+ item);
		System.out.println(">> onSkipInWrite : "+ t.getMessage());
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        System.out.println(">> onSkipInProcess : "+ item);
        System.out.println(">> onSkipInProcess : "+ t.getMessage());
    }
}
