package com.sapozhnikov.kickstarterData.entity;

public class FundedCountryProjects implements Comparable<FundedCountryProjects>{
    private String country;
    private Integer countSupported = 0;

    public FundedCountryProjects(String country){
        this.country = country;

    }

    public Integer getCountSupported() {
        return countSupported;
    }

    public void addCountSupported(int delta) {
        synchronized (this) {
            this.countSupported += delta;
        }
    }

    @Override
    public int compareTo(FundedCountryProjects fundedCountryProjects) {
        return fundedCountryProjects.getCountSupported().compareTo(countSupported);
    }

    public String getCountry() {
        return country;
    }
}
