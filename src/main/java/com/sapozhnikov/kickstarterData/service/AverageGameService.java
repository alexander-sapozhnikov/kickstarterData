package com.sapozhnikov.kickstarterData.service;

import com.sapozhnikov.kickstarterData.consumer.AverageGameByAll;
import com.sapozhnikov.kickstarterData.consumer.AverageGameByCountry;
import com.sapozhnikov.kickstarterData.entity.GameCountry;
import com.sapozhnikov.kickstarterData.entity.GameCountryResponse;
import com.sapozhnikov.kickstarterData.producer.SplitProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class AverageGameService {
    static final Logger log = LoggerFactory.getLogger(AverageGameService.class);


    public GameCountryResponse calcSingle(){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        SplitProducer consumer = new SplitProducer();
        executor.submit(consumer);

        ConcurrentHashMap<String, GameCountry> parsedData = new ConcurrentHashMap<>();
        AverageGameByCountry averageGameByCountry = new AverageGameByCountry(consumer, parsedData);
        executor.submit(averageGameByCountry);

        AverageGameByAll averageGameByAll = new AverageGameByAll(consumer);
        executor.submit(averageGameByAll);
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
       return new GameCountryResponse(new ArrayList<>(parsedData.values()), averageGameByAll.getAverage());
    }

    public GameCountryResponse calcMulti(){
        ExecutorService executor = Executors.newFixedThreadPool(5);

        SplitProducer consumer = new SplitProducer();
        executor.submit(consumer);

        ConcurrentHashMap<String, GameCountry> parsedData = new ConcurrentHashMap<>();
        executor.submit(new AverageGameByCountry(consumer, parsedData));
        executor.submit(new AverageGameByCountry(consumer, parsedData));
        executor.submit(new AverageGameByCountry(consumer, parsedData));

        AverageGameByAll averageGameByAll = new AverageGameByAll(consumer);
        executor.submit(averageGameByAll);
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return new GameCountryResponse(new ArrayList<>(parsedData.values()), averageGameByAll.getAverage());
    }
}
