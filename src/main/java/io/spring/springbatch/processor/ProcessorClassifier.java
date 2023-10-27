package io.spring.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.Map;

public class ProcessorClassifier<C, T> implements Classifier<C, T> {

    private Map<Integer, ItemProcessor>

    @Override
    public T classify(C classifiable) {
        return null;
    }
}
