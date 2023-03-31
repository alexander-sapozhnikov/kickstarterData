package com.sapozhnikov.kickstarterData.entity;

import java.util.List;

public class GameCountryResponse {
    private final List<GameCountry> gameCountryList;
    private long average = 0;

    public GameCountryResponse(List<GameCountry> gameCountryList, long average){
        this.gameCountryList = gameCountryList;
        this.average = average;
    }

    public List<GameCountry> getGameCountryList() {
        return gameCountryList;
    }

    public long getAverage() {
        return average;
    }
}
