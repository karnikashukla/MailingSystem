package com.intranet.mailingsystem.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserInboxController {

    @GetMapping("/inbox")
    public String displayInbox(ModelMap modelMap, HttpSession session){

        if (session.getAttribute("userId") != null){
            return "inbox";
        }
        else {
            return "redirect:/users/login";
        }
        //service api of retriving particular user's inbox mail
        //sending list of MailModel to frontend.

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("userId");
        return "redirect:/users/login";
    }
}
