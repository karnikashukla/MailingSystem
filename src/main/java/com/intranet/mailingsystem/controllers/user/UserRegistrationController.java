package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.Corporation;
import com.intranet.mailingsystem.models.RegistrationModel;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.repositories.UserRepository;
import com.intranet.mailingsystem.service.CorporationService;
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
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    @Autowired
    UserService userService;
    @Autowired
    CorporationService corporationService;

    @GetMapping("/registration")
    public String displayUserRegistrationPage(ModelMap modelMap){

        modelMap.addAttribute("userRegistration", new User());
        return "user_reg";
    }

    @PostMapping("/registration")
    public String doUserRegistration(@ModelAttribute("userRegistration") User user, ModelMap modelMap, Corporation corporation) {
        String registerError = null;

        if(userService.getUserByEmail(user.getEmail()) == null){
            if(!user.getEmail().equals(user.getAlternateEmail())){
                if(corporationService.findByCorporationName(user.getCorporationName()) != null){

                    corporation = corporationService.findByCorporationName(user.getCorporationName());

                    String domain = corporation.getDomain().substring(corporation.getDomain().indexOf("@") + 1);
                    Pattern emailFinder = Pattern.compile(".*" + domain);
                    Matcher emailMatcher = emailFinder.matcher(user.getEmail());

                    if(emailMatcher.find()){

                        userService.save(user);
                        return "redirect:/users/login";
                    }
                    else {
                        registerError = "Your email does not match with domain registered with us.";
                        modelMap.addAttribute("registerError",registerError);
                        return "user_reg";
                    }
                }
                else {
                    registerError = "Your Corporation is not registered yet to use our product.";
                    modelMap.addAttribute("registerError", registerError);
                    return "user_reg";
                }
            }
            else {
                modelMap.addAttribute("registerError", "Alternate Email cannot be same as Email!");
                return "user_reg";
            }

        }
        else{
            registerError = "Email already exists.";
            modelMap.addAttribute("registerError", registerError);
            return "user_reg";
        }

    }
}
