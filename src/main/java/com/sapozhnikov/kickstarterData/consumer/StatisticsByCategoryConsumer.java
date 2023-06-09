package com.sapozhnikov.kickstarterData.consumer;

import com.sapozhnikov.kickstarterData.entity.StatisticsByCategory;
import com.sapozhnikov.kickstarterData.repository.CsvRepository;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class StatisticsByCategoryConsumer extends SimpleConsumer<StatisticsByCategory> {
    static final Logger log = LoggerFactory.getLogger(StatisticsByCategoryConsumer.class);

    public StatisticsByCategoryConsumer(CsvRepository csvRepository, ConcurrentHashMap<String, StatisticsByCategory> parsedData) {
        super(csvRepository, parsedData);
    }


    @Override
    public void run() {
        while (true) {
            CSVRecord data = this.csvRepository.getQueue().poll();
            if (data == null && this.csvRepository.IsAll())
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
