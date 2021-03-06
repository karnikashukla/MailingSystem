package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserContactController {
    @Autowired
    UserService userService;

    @GetMapping("/contacts")
    public String displayContactPage(ModelMap modelMap, HttpSession session){
        if(session.getAttribute("userId") != null){
            long userId = (long) session.getAttribute("userId");
            User user =  userService.getUserById(userId);
            modelMap.addAttribute("firstName", user.getFirstName());
            modelMap.addAttribute("lastName", user.getLastName());
            List<User> userList = userService.findUsersByCorporations(user.getCorporationName());
            userList.removeIf(user1 -> user1.getId() == userId);
            modelMap.addAttribute("userList", userList);
            return "contact-inbox";
        }
        else {
            return "redirect:/users/login";
        }
    }

    @GetMapping("/send/{toEmail}")
    public String sendMailFromContact(@PathVariable("toEmail") String email, HttpSession session){
        session.setAttribute("toEmail", email);
        return "redirect:/users/compose";
    }
}
