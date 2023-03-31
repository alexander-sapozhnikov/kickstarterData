package com.sapozhnikov.kickstarterData.consumer;

import com.sapozhnikov.kickstarterData.entity.StatisticsByCategory;
import com.sapozhnikov.kickstarterData.repository.CsvData;

import java.util.concurrent.ConcurrentHashMap;

public abstract class  SimpleConsumer <E extends Comparable<E>> implements Runnable  {
    protected final CsvData csvData;
    protected final ConcurrentHashMap<String, E> parsedData;

    public SimpleConsumer(CsvData csvData, ConcurrentHashMap<String, E> parsedData) {
        this.csvData = csvData;
        this.parsedData = parsedData;
    }

}
