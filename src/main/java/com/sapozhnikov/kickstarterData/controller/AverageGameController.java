package com.sapozhnikov.kickstarterData.controller;

import com.sapozhnikov.kickstarterData.entity.GameCountryResponse;
import com.sapozhnikov.kickstarterData.service.AverageGameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AverageGameController {
    @RequestMapping(value = "/average_game")
    public String controller(@RequestParam(name = "type", required = false, defaultValue = "single") String type, Model model) {
        long start = System.currentTimeMillis();
        GameCountryResponse ans = null;
        if ("single".equals(type)) {
            ans = new AverageGameService().calcSingle();
            model.addAttribute("isSingle", true);
        } else {
            ans = new AverageGameService().calcMulti();
            model.addAttribute("isSingle", false);
        }

        model.addAttribute("data", ans.getGameCountryList());
        model.addAttribute("average", ans.getAverage());
        model.addAttribute("timeExecute", System.currentTimeMillis() - start);
        return "average_game";
    }
}
