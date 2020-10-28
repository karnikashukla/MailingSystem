package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.ChangePassword;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserChangePassword {
    @Autowired
    UserService userService;
    @GetMapping("/new/password")
    public String displayChangePassword(ModelMap modelMap, HttpSession session){
        modelMap.addAttribute("changePassword", new ChangePassword());
        return "change-password";
    }

    @PostMapping("/new/password")
    public String changePassword(@ModelAttribute("changePassword") ChangePassword changePassword, HttpSession session, ModelMap modelMap){
        if (changePassword.getOTP().equals((String) session.getAttribute("OTP"))){
            User user = userService.getUserById((long) session.getAttribute("changeUserId"));
            userService.editPassword(user, changePassword.getNewPassword());
            return "redirect:/users/login";
        }
        else {
            modelMap.addAttribute("changeError", "Invalid OTP");
            return "change-password";
        }

    }
}
