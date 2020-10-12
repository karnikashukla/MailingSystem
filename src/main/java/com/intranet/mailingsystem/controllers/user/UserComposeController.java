package com.intranet.mailingsystem.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserComposeController {
    @GetMapping("/compose")
    public String displayComposePage(){
        return "/users/User_Compose";
    }
}
