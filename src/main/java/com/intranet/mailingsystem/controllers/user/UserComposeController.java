package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.MailService;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/users")
public class UserComposeController {
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @GetMapping("/compose")
    public String displayComposePage(HttpSession session, ModelMap modelMap, User user){
        if (session.getAttribute("userId") != null){
            User user1 = userService.getUserById((long) session.getAttribute("userId"));

            modelMap.addAttribute("fromMail",user1.getEmail());
            modelMap.addAttribute("compose", new Mail(user1.getEmail()));
            return "/compose";
        }
        else {
            return "redirect:/users/login";
        }


    }

    @PostMapping("/compose")
    public String sendEmail(@ModelAttribute("compose") Mail mail, HttpSession session){
        long id= (long) session.getAttribute("userId");
        String fromMail = userService.getUserById(id).getEmail();
        List<String> toMailList = Arrays.asList(mail.getToMail().split(","));
        Mail tempMail = new Mail();
        int counter = 15;

        for(String toMail: toMailList){
            String domain =  fromMail.substring(fromMail.indexOf("@") + 1);
            Pattern emailFinder = Pattern.compile(domain);
            Matcher toMailMatcher = emailFinder.matcher(toMail);
            Matcher fromMailMatcher = emailFinder.matcher(fromMail);

            if(toMailMatcher.find() && fromMailMatcher.find()){
                tempMail.setMailId(counter);
                counter++;

                tempMail.setFromMail(mail.getFromMail());
                tempMail.setSubject(mail.getSubject());
                tempMail.setBody(mail.getBody());

                tempMail.setToMail(toMail);
                mailService.save(tempMail);
                System.out.println("Done");
            }
            else {
                continue;
            }

        }

        return "redirect:/users/compose";
    }
}


//    @([\\w\\.]+\\.\\w+)
//    Pattern emailFinder = Pattern.compile("gmail.com");
//    Matcher emailmatcher = emailFinder.matcher(email1);