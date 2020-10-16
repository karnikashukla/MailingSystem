package com.intranet.mailingsystem.controllers.user;


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
public class UserProfileController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String showProfile(ModelMap modelMap, HttpSession session, User user){
        if (session.getAttribute("userId")!= null){
            user = userService.getUserById((long) session.getAttribute("userId"));
            modelMap.addAttribute("user", user);
            return "user_profile";
        }
        else {
            return "redirect:/users/login";
        }


    }

    @PostMapping("/profile")
    public String editProfile(@ModelAttribute("user") User user, ModelMap modelMap, HttpSession session){
        if (session.getAttribute("userId") != null){
            userService.editUserProfile(user, user.getId());
//            System.out.println("::: User 1 ::: Id ::: " + user1.getId() + " ::: LastName ::: " + user1.getLastName());
        }
        return "redirect:/users/profile";
    }
}
