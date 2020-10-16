package com.intranet.mailingsystem.controllers.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserProfileController {

    @GetMapping("/profile")
    public String showProfile(){
        return "user_profile";
    }
}
