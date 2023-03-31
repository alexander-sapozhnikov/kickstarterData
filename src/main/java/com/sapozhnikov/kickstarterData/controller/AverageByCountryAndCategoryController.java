package com.sapozhnikov.kickstarterData.controller;

import com.sapozhnikov.kickstarterData.entity.AverageByCountryAndCategory;
import com.sapozhnikov.kickstarterData.entity.GameCountryResponse;
import com.sapozhnikov.kickstarterData.service.AverageByCountryAndCategoryService;
import com.sapozhnikov.kickstarterData.service.AverageGameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AverageByCountryAndCategoryController {
    @RequestMapping(value = "/average_country_category")
    public String controller(@RequestParam(name = "type", required = false, defaultValue = "single") String type, Model model) {
        long start = System.currentTimeMillis();
        List<AverageByCountryAndCategory> ans = null;
        if ("single".equals(type)) {
            ans = new AverageByCountryAndCategoryService().calcSingle();
            model.addAttribute("isSingle", true);
        } else {
            ans = new AverageByCountryAndCategoryService().calcMulti();
            model.addAttribute("isSingle", false);
        }

        model.addAttribute("data", ans);
        model.addAttribute("timeExecute", System.currentTimeMillis() - start);
        return "average_country_category";
    }
}
