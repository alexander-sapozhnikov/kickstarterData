package com.sapozhnikov.kickstarterData.consumer;

import com.sapozhnikov.kickstarterData.producer.SplitProducer;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class AverageGameByAll implements Runnable {
    static final Logger log = LoggerFactory.getLogger(FundedProjectsConsumer.class);

    private final SplitProducer splitProducer;
    private final HashMap<String, Long> parsedData;


    public AverageGameByAll(SplitProducer splitProduce) {
        this.splitProducer = splitProduce;
        this.parsedData = new HashMap<>();
    }

    @Override
    public void run() {
        while (true) {
            CSVRecord data = this.splitProducer.getQueueAverage().poll();
            if (data == null && this.splitProducer.IsAll())
                return;

            if (data == null) {
                continue;
            }

            log.info("AverageGameByAll worked on line {}", data.getRecordNumber());
            if (!"Games".equals(data.get("category_parent_name"))) {
                continue;
            }
            String country = data.get("country");
            Long count = parsedData.getOrDefault(country, 0L);
            count++;
            parsedData.put(country, count);
        }
    }

    public long getAverage() {
        long allCount = 0;
        for (Long count : parsedData.values()) {
            allCount += count;
        }

        return allCount / parsedData.size();
    }
}
