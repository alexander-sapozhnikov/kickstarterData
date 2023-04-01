package com.sapozhnikov.kickstarterData.service;

import com.sapozhnikov.kickstarterData.entity.AverageByCountryAndCategory;
import com.sapozhnikov.kickstarterData.entity.CountryAndCategory;
import com.sapozhnikov.kickstarterData.repository.CsvRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AverageByCountryAndCategoryService {

    public List<AverageByCountryAndCategory> calcSingle() {
        CsvRepository csvRepository = new CsvRepository();
        csvRepository.run();

        HashMap<CountryAndCategory, AverageByCountryAndCategory> res =
                (HashMap<CountryAndCategory, AverageByCountryAndCategory>) csvRepository.
                        getQueue().
                        stream().
                        collect(Collectors.toMap(
                                el -> new CountryAndCategory(el.get("country"), el.get("category_name")),
                                el -> {
                                    AverageByCountryAndCategory averageByCountryAndCategory = new AverageByCountryAndCategory(new CountryAndCategory(el.get("country"), el.get("category_name")));
                                    averageByCountryAndCategory.addCountSupported(Integer.parseInt(el.get("backers_count")));
                                    averageByCountryAndCategory.incCountAll();
                                    return averageByCountryAndCategory;
                                },
                                (el1, el2) -> {
                                    el1.addCountSupported(el2.getCountSupported());
                                    el1.incCountAll();
                                    return el1;
                                })
                        );
        return new ArrayList<>(res.values());
    }

    public List<AverageByCountryAndCategory> calcMulti() {
        CsvRepository csvRepository = new CsvRepository();
        csvRepository.run();

        HashMap<CountryAndCategory, AverageByCountryAndCategory> res =
                (HashMap<CountryAndCategory, AverageByCountryAndCategory>) csvRepository.
                        getQueue().
                        stream().
                        parallel().
                        collect(Collectors.toMap(
                                el -> new CountryAndCategory(el.get("country"), el.get("category_name")),
                                el -> {
                                    AverageByCountryAndCategory averageByCountryAndCategory = new AverageByCountryAndCategory(new CountryAndCategory(el.get("country"), el.get("category_name")));
                                    averageByCountryAndCategory.addCountSupported(Integer.parseInt(el.get("backers_count")));
                                    averageByCountryAndCategory.incCountAll();
                                    return averageByCountryAndCategory;
                                },
                                (el1, el2) -> {
                                    el1.addCountSupported(el2.getCountSupported());
                                    el1.incCountAll();
                                    return el1;
                                })
                        );
        return new ArrayList<>(res.values());
    }
}
