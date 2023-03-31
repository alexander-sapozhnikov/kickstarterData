package com.sapozhnikov.kickstarterData.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CsvData implements Runnable{
    static final Logger log = LoggerFactory.getLogger(CsvData.class);
    private final BlockingQueue<CSVRecord> queue;
    private boolean isAll = false;

    public CsvData() {
        queue = new LinkedBlockingDeque<>();
    }


    public BlockingQueue<CSVRecord> getQueue(){
        return queue;
    }

    @Override
    public void run(){
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("most_funded_feb_2023.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(reader);
            for (CSVRecord record : records) {
                queue.add(record);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        isAll = true;
    }

    public boolean IsAll(){
        return this.isAll;
    }
}