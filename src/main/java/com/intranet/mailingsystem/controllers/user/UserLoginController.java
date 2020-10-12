package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.LoginModel;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.repositories.UserRepository;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String doUserLogin(@ModelAttribute("userLogin") User user, HttpSession session, ModelMap modelMap){
        String loginError = "Email or Password invalid.";
        if(userService.getUserById(user.getEmail()) != null){
            User user1 = userService.getUserById(user.getEmail());
            if(user1.getPassword().equals(user.getPassword())){
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
//         String email = "abc@gmail.com";
//         String password = "123";
//         String loginError = "Email or Password invalid.";
//
//        if (email.equals(loginModel.getEmail())&& password.equals(loginModel.getPassword()))
//        {
//            session.setAttribute("email",email);
//            return "redirect:/users/inbox";
//        }
//        else {
//            modelMap.addAttribute("loginError",loginError);
//            return "user_login";
//        }
    }

}

