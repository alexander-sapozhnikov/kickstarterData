package com.sapozhnikov.kickstarterData.producer;

import com.sapozhnikov.kickstarterData.repository.CsvRepository;
import org.apache.commons.csv.CSVRecord;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class SplitProducer implements Runnable{

    private final BlockingQueue<CSVRecord> queueAll, queueAverage;
    private boolean isAll = false;

    public SplitProducer() {
        queueAll = new LinkedBlockingDeque<>();
        queueAverage = new LinkedBlockingDeque<>();
    }

    public BlockingQueue<CSVRecord> getQueueAll(){
        return queueAll;
    }
    public BlockingQueue<CSVRecord> getQueueAverage(){
        return queueAverage;
    }

    @Override
    public void run(){
        CsvRepository data =  new CsvRepository();
        data.run();
        BlockingQueue<CSVRecord> queue = data.getQueue();
        while (true){
            CSVRecord el = queue.poll();
            if (el == null) {
                isAll = true;
                return;
            }
            queueAll.add(el);
            queueAverage.add(el);
        }
    }

    public boolean IsAll(){
        return this.isAll;
    }
}
