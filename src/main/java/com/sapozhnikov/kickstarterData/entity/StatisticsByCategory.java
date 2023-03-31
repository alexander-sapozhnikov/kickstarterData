package com.sapozhnikov.kickstarterData.entity;

public class StatisticsByCategory implements Comparable<StatisticsByCategory>{
    private String category;
    private Integer count= 0;

    public StatisticsByCategory(String category){
        this.category = category;

    }

    public Integer getCount() {
        return count;
    }

    public void incCount() {
        synchronized (this) {
            this.count ++;
        }
    }

    @Override
    public int compareTo(StatisticsByCategory statisticsByCategory) {
        return statisticsByCategory.getCount().compareTo(count);
    }

    public String getCategory() {
        return category;
    }
}
