package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.RegistrationModel;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.repositories.UserRepository;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String displayUserRegistrationPage(ModelMap modelMap){

        modelMap.addAttribute("userRegistration", new User());
        return "user_reg";
    }

    @PostMapping("/registration")
    public String doUserRegistration(@ModelAttribute("userRegistration") User user,ModelMap modelMap) {
        if(userService.getUserByEmail(user.getEmail()) == null){
            userService.save(user);
            return "redirect:/users/login";
        }
        else{
            modelMap.addAttribute("registerError","email already exists. please try login.");
            return "user_reg";
        }

    }
}
