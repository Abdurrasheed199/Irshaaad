package com.irshaaad.trans.controller;

import com.irshaaad.trans.model.User;
import com.irshaaad.trans.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/dashboards/admin")
    public String dashboard(Model model, Authentication authentication) {

        String username = authentication.getName();
        User u = userRepository.findUserByUsername(username);
        model.addAttribute("user", u );
        return "dashboard/dashboard";
    }


    @GetMapping("/layout")
    public  String layout(Model model){
        return "/layout";
    }

    /*@GetMapping("/list")
    public  String about(Model model){
        return "/list";
    }*/

    @GetMapping("/about us")
    public  String us(Model model){
        return "/about us";
    }

    @GetMapping("/contact us")
    public  String contact(Model model){
        return "/contact us";
    }





}