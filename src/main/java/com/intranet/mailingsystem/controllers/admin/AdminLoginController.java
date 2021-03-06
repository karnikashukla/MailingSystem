package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.LoginModel;
import com.intranet.mailingsystem.sequencegenerator.SequenceGeneratorService;
import com.intranet.mailingsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    AdminService adminService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;



    @GetMapping("/login")
    public String displayAdminLoginPage(ModelMap modelMap){

        modelMap.addAttribute("adminLogin",new Admin());
        return "login";
    }
    @PostMapping("/login")
    public String doAdminLogin(@ModelAttribute("adminLogin") Admin admin, HttpSession session, ModelMap modelMap){
       String loginError = "Email or Password invalid.";
       if(adminService.getAdminByEmail(admin.getEmail()) != null){
           Admin admin1 = adminService.getAdminByEmail(admin.getEmail());
           if(admin1.getPassword().equals(admin.getPassword())){
               session.setAttribute("adminId", admin1.getId());
               return "redirect:/admin/dashboard";
           }
           else {
               modelMap.addAttribute("PasswordError","Password is wrong.");
               System.out.println("PasswordError");
               return "login";
           }
       }
       else {
           modelMap.addAttribute("EmailError","Email is invalid.");
           System.out.println("EmailError");
           return "login";
       }
    }

    @GetMapping("/save")
    public void save(){
        Admin a = new Admin();
        sequenceGeneratorService.generateSequence(Admin.SEQUENCE_NAME);
        a.setFirstName("Karnika");
        a.setLastName("Shukla");
        a.setEmail("admin-karnika@mailing.com");
        a.setPassword("Karnika123!");
        a.setAlternateEmail("karnikashukla25@gmail.com");
        adminService.save(a);
    }

    @GetMapping("/logout")
    public String adminLogout(HttpSession session){
        session.removeAttribute("adminId");
        return "redirect:/admin/login";
    }


}
