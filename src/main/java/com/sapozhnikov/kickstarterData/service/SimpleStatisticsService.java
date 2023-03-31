package com.sapozhnikov.kickstarterData.service;

import com.sapozhnikov.kickstarterData.consumer.SimpleConsumer;
import com.sapozhnikov.kickstarterData.repository.CsvData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleStatisticsService<C extends SimpleConsumer, E extends Comparable<E>> {
    static final Logger log = LoggerFactory.getLogger(SimpleStatisticsService.class);

    private CsvData csvData = null;
    private static Collection<Thread>  allThreadCollection;
    private final ConcurrentHashMap<String, E> parsedData;

    private final Class<C> clazz;

    public SimpleStatisticsService(Class<C> clazz){
        allThreadCollection = new ArrayList<>();
        parsedData = new ConcurrentHashMap<>();
        this.clazz = clazz;
    }

    public List<E> calcSingle(){
        createAndStartProducers();
        // wait reading
        for(Thread t: allThreadCollection){
            try {
                t.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        createAndStartConsumers(1);

        return collectData();
    }

    public List<E> calcMulti(){
        createAndStartProducers();
        createAndStartConsumers(3);
        return collectData();
    }

    private  void createAndStartProducers(){
        csvData = new CsvData();
        Thread producerThread = new Thread(csvData,"read_data");
        producerThread.start();
        allThreadCollection.add(producerThread);
    }


    private void createAndStartConsumers(int count) {
        for (int i = 0; i <= count; i++) {
            C consumer = null;
            try {
                consumer = this.clazz.getDeclaredConstructor(csvData.getClass(), parsedData.getClass()).newInstance(csvData, parsedData);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            Thread consumerThread = new Thread(consumer, "consumer " + i);
            allThreadCollection.add(consumerThread);
            consumerThread.start();
        }
    }

    private List<E> collectData(){
        for(Thread t: allThreadCollection){
            try {
                t.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        List<E> list = new ArrayList<>(parsedData.values());
        Collections.sort(list);
        return list;
    }

}
