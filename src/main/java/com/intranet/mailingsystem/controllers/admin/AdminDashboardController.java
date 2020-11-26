package com.intranet.mailingsystem.controllers.admin;


import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.AdminService;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    @GetMapping("/dashboard")
    public String displayDashboard(HttpSession session, ModelMap modelMap){
        if(session.getAttribute("adminId") != null){
            Admin admin = adminService.getAdminById((long) session.getAttribute("adminId"));
            int userCount = userService.getAllUsers().size();
            modelMap.addAttribute("userCount", userCount);
            modelMap.addAttribute("firstName", admin.getFirstName());
            modelMap.addAttribute("lastName", admin.getLastName());
            return "/admin-index";
        }
        else {
            return "redirect:/admin/login";
        }

    }
}
