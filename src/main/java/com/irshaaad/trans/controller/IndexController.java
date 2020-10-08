package com.irshaaad.trans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/dashboards/admin")
    public String dashboard(Model model) {
        return "dashboard/dashboard";
    }

    @GetMapping("/layout")
    public  String layout(Model model){
        return "/layout";
    }

    @GetMapping("/about")
    public  String about(Model model){
        return "/about";
    }





}