package com.sapozhnikov.kickstarterData.entity;

public class GameCountry {
    private final String country;
    private Integer countGames = 0;

    public GameCountry(String country){
        this.country = country;

    }

    public void incCountGames() {
        countGames++;
    }

    public String getCountry() {
        return country;
    }

    public Integer getCountGames() {
        return countGames;
    }
}
