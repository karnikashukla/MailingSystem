package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.LoginModel;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/users")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String displayUserLoginPage(ModelMap model){
        model.addAttribute("userLogin",new LoginModel());
        return "user_login";
    }

    @PostMapping("/login")
    public String doUserLogin(@ModelAttribute("userLogin") LoginModel loginModel, ModelMap modelMap, HttpSession session){

        System.out.println("User name : " + loginModel.getEmail());

            if(userService.getUserByEmail(loginModel.getEmail()) != null){
                User user = userService.getUserByEmail(loginModel.getEmail());

                if(user.getPassword().equals(loginModel.getPassword())){
                    session.setAttribute("userId", user.getId());
                    System.out.println("Password match");
                    return "redirect:/users/inbox";
                }
                else {

                    modelMap.addAttribute("PasswordError","Password is wrong.");
                    System.out.println("PasswordError");
                    return "user_login";
                }
            }
            else {
                modelMap.addAttribute("EmailError","Email is invalid.");
                System.out.println("EmailError");
                return "user_login";
            }

    }

}

