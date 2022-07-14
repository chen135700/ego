package com.ego.portal.controller;

import com.ego.portal.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PortalControler {
    @Autowired
    private PortalService portalService;
    @RequestMapping("/")
    public String welcome(Model model){
        model.addAttribute("ad1",portalService.showBigAd());
        return "index";
    }

    @RequestMapping("/bigad")
    @ResponseBody
    public String bigad(){
        System.out.println("执行了bigad2");
        portalService.showBigAd2();
        return "ok";
    }
}
