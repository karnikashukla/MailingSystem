package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.fileconvertors.UserPDFExporter;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.UserService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/users/list/{corporationName}")
    public String displayUsersThatBelongToACorporation(@PathVariable("corporationName") String corporationName, ModelMap modelMap, HttpSession session){
        if(session.getAttribute("adminId") != null){
            List<User> userList = userService.findUsersByCorporations(corporationName);
            modelMap.addAttribute("userList", userList);
            return "admin-userList";
        }
        else {
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable("userId") long userId, ModelMap modelMap, HttpSession session){
        if(session.getAttribute("adminId") != null){
            userService.deleteUser(userId);
            modelMap.addAttribute("userDeletedSuccessfully", " User Deleted Successfully");
            return "redirect:/admin/users/list";
        }
        else {
            return "redirect:/admin/login";
        }
    }
}
