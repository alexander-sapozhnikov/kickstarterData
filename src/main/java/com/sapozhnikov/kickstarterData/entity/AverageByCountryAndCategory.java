package com.sapozhnikov.kickstarterData.entity;

public class AverageByCountryAndCategory {
    private final CountryAndCategory countryAndCategory;
    private int countSupported = 0;
    private int countAll = 0;

    public AverageByCountryAndCategory(CountryAndCategory countryAndCategory){
        this.countryAndCategory = countryAndCategory;
    }

    public void addCountSupported(int delta){
        this.countSupported += delta;
    }

    public void incCountAll(){
        this.countAll++;
    }

    public int getAverage() {
        return countSupported / countAll;
    }

    public int getCountSupported(){
        return countSupported;
    }

    public CountryAndCategory getCountryAndCategory() {
        return countryAndCategory;
    }
}
