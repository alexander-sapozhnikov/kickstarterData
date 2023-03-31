package com.sapozhnikov.kickstarterData.consumer;

import com.sapozhnikov.kickstarterData.entity.StatisticsByCategory;
import com.sapozhnikov.kickstarterData.repository.CsvData;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class StatisticsByCategoryConsumer extends SimpleConsumer<StatisticsByCategory> {
    static final Logger log = LoggerFactory.getLogger(StatisticsByCategoryConsumer.class);

    public StatisticsByCategoryConsumer(CsvData csvData, ConcurrentHashMap<String, StatisticsByCategory> parsedData) {
        super(csvData, parsedData);
    }


    @Override
    public void run() {
        while (true) {
            CSVRecord data = this.csvData.getQueue().poll();
            if (data == null && this.csvData.IsAll())
                return;

            if (data == null) {
                continue;
            }

            log.info("I worked on line {}", data.getRecordNumber());
            String category = data.get("category_name");
            StatisticsByCategory defaultValue = new StatisticsByCategory(category);
            synchronized (this.parsedData) {
                StatisticsByCategory statisticsByCategory = this.parsedData.getOrDefault(category, defaultValue);
                statisticsByCategory.incCount();

                parsedData.put(category, statisticsByCategory);
            }


        }
    }
}
