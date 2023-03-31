package com.sapozhnikov.kickstarterData.consumer;

import com.sapozhnikov.kickstarterData.entity.GameCountry;
import com.sapozhnikov.kickstarterData.producer.SplitProducer;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class AverageGameByCountry implements Runnable {
    static final Logger log = LoggerFactory.getLogger(FundedProjectsConsumer.class);
    private final SplitProducer splitProducer;
    private final ConcurrentHashMap<String, GameCountry> parsedData;

    public AverageGameByCountry(SplitProducer splitProduce, ConcurrentHashMap<String, GameCountry> parsedData) {
        this.splitProducer = splitProduce;
        this.parsedData = parsedData;
    }

    @Override
    public void run() {
        while (true) {
            CSVRecord data = this.splitProducer.getQueueAll().poll();
            if (data == null && this.splitProducer.IsAll())
                return;

            if (data == null) {
                continue;
            }

            log.info("AverageGameByCountry worked on line {}", data.getRecordNumber());
            String country = data.get("country");
            GameCountry defaultValue = new GameCountry(country);
            synchronized (this.parsedData) {
                GameCountry gameCountry = this.parsedData.getOrDefault(country, defaultValue);

                if (!"Games".equals(data.get("category_parent_name"))) {
                    continue;
                }

                gameCountry.incCountGames();
                parsedData.put(country, gameCountry);
            }

        }
    }
}
