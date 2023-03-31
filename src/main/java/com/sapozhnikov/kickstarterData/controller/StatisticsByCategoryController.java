package com.sapozhnikov.kickstarterData.controller;

import com.sapozhnikov.kickstarterData.consumer.StatisticsByCategoryConsumer;
import com.sapozhnikov.kickstarterData.entity.StatisticsByCategory;
import com.sapozhnikov.kickstarterData.service.SimpleStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StatisticsByCategoryController {
    @RequestMapping(value = "/statistics_by_category")
    public String controller(@RequestParam(name = "type", required = false, defaultValue = "single") String type, Model model) {
        long start = System.currentTimeMillis();
        List<StatisticsByCategory> ans;
        if ("single".equals(type)){
            ans = new SimpleStatisticsService<StatisticsByCategoryConsumer, StatisticsByCategory>(StatisticsByCategoryConsumer.class).calcSingle();
            model.addAttribute("isSingle", true);
        } else {
            ans = new SimpleStatisticsService<StatisticsByCategoryConsumer, StatisticsByCategory>(StatisticsByCategoryConsumer.class).calcMulti();
            model.addAttribute("isSingle", false);

        }

        model.addAttribute("data", ans);
        model.addAttribute("timeExecute", System.currentTimeMillis()-start);
        return "statistics_by_category";
    }

}