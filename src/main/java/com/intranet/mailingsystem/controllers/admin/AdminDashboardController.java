package com.intranet.mailingsystem.controllers.admin;


import com.intranet.mailingsystem.service.AdminService;
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

    @GetMapping("/dashboard")
    public String displayDashboard(HttpSession session, ModelMap modelMap){
        if(adminService.getAdminById((long) session.getAttribute("adminId")) != null){
            return "/admin-index";
        }
        else {
            return "redirect:/admin/login";
        }

    }
}
