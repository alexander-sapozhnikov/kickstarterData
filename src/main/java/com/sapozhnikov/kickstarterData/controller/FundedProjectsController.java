package com.sapozhnikov.kickstarterData.controller;

import com.sapozhnikov.kickstarterData.consumer.FundedProjectsConsumer;
import com.sapozhnikov.kickstarterData.entity.FundedCountryProjects;
import com.sapozhnikov.kickstarterData.service.AverageByCountryAndCategoryService;
import com.sapozhnikov.kickstarterData.service.SimpleStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FundedProjectsController {
    @RequestMapping(value = "/funded_projects")
    public String controller(@RequestParam(name = "type", required = false, defaultValue = "single") String type, Model model) {

        new AverageByCountryAndCategoryService().calcSingle();
        long start = System.currentTimeMillis();
        List<FundedCountryProjects> ans;
        if ("single".equals(type)){
            ans = new SimpleStatisticsService<FundedProjectsConsumer, FundedCountryProjects>(FundedProjectsConsumer.class).calcSingle();
            model.addAttribute("isSingle", true);
        } else {
            ans = new SimpleStatisticsService<FundedProjectsConsumer, FundedCountryProjects>(FundedProjectsConsumer.class).calcMulti();
            model.addAttribute("isSingle", false);
        }

        model.addAttribute("data", ans);
        model.addAttribute("timeExecute", System.currentTimeMillis()-start);
        return "funded_projects";
    }

}