package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.MailService;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserSentItemsController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @GetMapping("/sent")
    public String showSentItems(ModelMap modelMap, HttpSession session, User user){
        modelMap.addAttribute("mailError", null);
        if (session.getAttribute("userId") != null){

            user = userService.getUserById((long) session.getAttribute("userId"));
            modelMap.addAttribute("firstName", user.getFirstName());
            modelMap.addAttribute("lastName", user.getLastName());

            if(mailService.findAllMailsOfUserSent(user.getEmail()) != null){
                List<Mail> mailList = mailService.findAllMailsOfUserSent(user.getEmail());
                modelMap.addAttribute("mailList", mailList);
            }
            else{
                modelMap.addAttribute("mailError", "cannot display data!");
            }
            return "user_sent";

        }
        else {
            return "redirect:/users/login";
        }

    }
}
