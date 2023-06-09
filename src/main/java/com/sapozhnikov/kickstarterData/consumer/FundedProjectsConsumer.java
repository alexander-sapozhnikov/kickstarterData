package com.sapozhnikov.kickstarterData.consumer;

import com.sapozhnikov.kickstarterData.entity.FundedCountryProjects;
import com.sapozhnikov.kickstarterData.repository.CsvRepository;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class FundedProjectsConsumer extends SimpleConsumer<FundedCountryProjects> {
    static final Logger log = LoggerFactory.getLogger(FundedProjectsConsumer.class);

    public FundedProjectsConsumer(CsvRepository csvRepository, ConcurrentHashMap<String, FundedCountryProjects> parsedData) {
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
            String country = data.get("country");
            FundedCountryProjects defaultValue = new FundedCountryProjects(country);
            synchronized (this.parsedData) {
                FundedCountryProjects fundedCountryProjects = this.parsedData.getOrDefault(country, defaultValue);
                String backersCountStr = data.get("backers_count");

                int backersCount = 0;
                try {
                    backersCount = Integer.parseInt(backersCountStr);
                } catch (NumberFormatException e) {
                    log.error(e.getMessage());
                }

                fundedCountryProjects.addCountSupported(backersCount);

                parsedData.put(country, fundedCountryProjects);

            }

        }
    }
}
