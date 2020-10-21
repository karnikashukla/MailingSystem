package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.fileconvertors.UserPDFExporter;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.UserService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserListController {
    @Autowired
    UserService userService;

    @GetMapping("/users/list")
    public String showUsers(ModelMap modelMap){
        modelMap.addAttribute("userList", userService.getAllUsers());
        return "admin-userList";
    }

    @GetMapping("/users/list/download")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_list_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.getAllUsers();

        UserPDFExporter exporter = new UserPDFExporter(listUsers);
        exporter.export(response);

    }
}
