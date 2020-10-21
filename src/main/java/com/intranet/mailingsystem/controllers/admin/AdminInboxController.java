package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.models.User;
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
public class AdminInboxController {
    @Autowired
    AdminService adminService;
    @Autowired
    MailService mailService;
    @GetMapping("/inbox")
    public String displayAdminInbox(ModelMap modelMap, HttpSession session){
        modelMap.addAttribute("mailError", null);
        if (session.getAttribute("adminId") != null){

            Admin admin = adminService.getAdminById((long) session.getAttribute("adminId"));
            modelMap.addAttribute("firstName", admin.getFirstName());
            modelMap.addAttribute("lastName", admin.getLastName());

            if(mailService.findAllMailsOfAdminInbox(admin.getEmail()) != null){

                List<Mail> mailList = mailService.findAllMailsOfAdminInbox(admin.getEmail());
                modelMap.addAttribute("mailList", mailList);

            }
            else{
                modelMap.addAttribute("mailError", "cannot display data!");
                //cannot display mails message here
            }
            return "/admin-inbox";

        }
        else {
            return "redirect:/users/login";
        }

    }


}
