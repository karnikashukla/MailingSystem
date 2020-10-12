package com.intranet.mailingsystem.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminInboxController {
    @GetMapping("/inbox")
    public String displayAdminInbox(){
        return "/admin-index";
    }
}
