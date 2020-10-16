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
public class UserInboxController {
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

    @GetMapping("/inbox")
    public String displayInbox(ModelMap modelMap, HttpSession session) throws Exception{
        modelMap.addAttribute("mailError", "");
        if (session.getAttribute("userId") != null){
            User user = userService.getUserById((long) session.getAttribute("userId"));

            if(mailService.findAllMailsOfUser(user.getEmail()) != null){
                List<Mail> mailList = mailService.findAllMailsOfUser(user.getEmail());
                modelMap.addAttribute("mailList", mailList);
                System.out.println(user.getEmail());
                return "inbox";
            }
            else{
                //cannot display mails message here
                return "inbox";
            }

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
