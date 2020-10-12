package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserForgotPassword {

    @GetMapping("/forgot")
    public String ForgotPassword(@ModelAttribute("userForgot") User user, ModelMap modelMap){
        return "forgot_password";
    }
}
