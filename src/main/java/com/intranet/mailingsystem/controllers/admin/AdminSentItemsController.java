package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.service.AdminService;
import com.intranet.mailingsystem.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminSentItemsController {

    @Autowired
    AdminService adminService;
    @Autowired
    MailService mailService;


    @GetMapping("/sent")
    public String displayAdminSentItems(ModelMap modelMap, HttpSession session, Admin admin){
        modelMap.addAttribute("mailError", null);
        if (session.getAttribute("adminId") != null){

            admin = adminService.getAdminById((long) session.getAttribute("adminId"));
            modelMap.addAttribute("firstName", admin.getFirstName());
            modelMap.addAttribute("lastName", admin.getLastName());

            if(mailService.findAllMailsOfAdminSent(admin.getEmail()) != null){
                List<Mail> mailList = mailService.findAllMailsOfAdminSent(admin.getEmail());
                modelMap.addAttribute("mailList", mailList);
            }
            else{
                modelMap.addAttribute("mailError", "cannot display data!");
            }
            return "admin-sent";

        }
        else {
            return "redirect:/admin/login";
        }
    }

}
