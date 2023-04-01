package com.sapozhnikov.kickstarterData.consumer;

import com.sapozhnikov.kickstarterData.repository.CsvRepository;

import java.util.concurrent.ConcurrentHashMap;

public abstract class  SimpleConsumer <E extends Comparable<E>> implements Runnable  {
    protected final CsvRepository csvRepository;
    protected final ConcurrentHashMap<String, E> parsedData;

    public SimpleConsumer(CsvRepository csvRepository, ConcurrentHashMap<String, E> parsedData) {
        this.csvRepository = csvRepository;
        this.parsedData = parsedData;
    }

}
