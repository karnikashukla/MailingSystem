package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserSentItemsController {

    @Autowired
    private UserService userService;

    @GetMapping("/sent")
    public String showSentItems(ModelMap modelMap, HttpSession session){

//        String id = (String)session.getAttribute("userId");
//        /*yet to change ,findUserByEmail to findUserById*/
//        User user = userService.getUserByEmail(id);
//        modelMap.addAttribute("firstName",user.getFirstName());
//        modelMap.addAttribute("lastName",user.getLastName());
//        System.out.println(id);
        return "user_sent";
    }
}
