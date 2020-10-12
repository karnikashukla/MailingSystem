package com.intranet.mailingsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.GET;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String displayHome(Model model){
        return "homepage";
    }
}
