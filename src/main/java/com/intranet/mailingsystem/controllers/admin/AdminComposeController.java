package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.service.AdminService;
import com.intranet.mailingsystem.service.MailService;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin")
public class AdminComposeController {
    @Autowired
    AdminService adminService;
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

        @GetMapping("/compose")
        public String displayComposePage(ModelMap modelMap, HttpSession session, Admin admin){
            if(adminService.getAdminById((long) session.getAttribute("adminId")) != null){
                admin = adminService.getAdminById((long) session.getAttribute("adminId"));
                modelMap.addAttribute("firstName", admin.getFirstName());
                modelMap.addAttribute("lastName", admin.getLastName());
                modelMap.addAttribute("fromMail",admin.getEmail());
                modelMap.addAttribute("compose", new Mail(admin.getEmail()));
                return "/admin-compose";
            }
            else{
                modelMap.addAttribute("loginError", "Login First!");
                return "redirect:/admin/login";
            }
        }

    @RequestMapping(value = "/compose", consumes = {"multipart/form-data"},method = RequestMethod.POST)
    public String sendAdminEmail(@Valid @ModelAttribute("compose") Mail mail, HttpSession session, ModelMap modelMap, @RequestParam(value = "file") MultipartFile[] multipartFiles) {
        if (session.getAttribute("adminId") != null) {
            long id = (long) session.getAttribute("adminId");

            Admin admin = adminService.getAdminById(id);

            String fromMail = admin.getEmail();

            // Getting current System time
            LocalDateTime current = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
            String formatted = current.format(formatter);


            // Storing file in specific folder
            formatted = formatted.replaceAll("[^\\w\\s]", " ");
            String UPLOADED_FOLDER = "E://Intranet/admin/" + admin.getId() + "/" + formatted;
            List<String> tempFileNames = new ArrayList<String>();

            if (multipartFiles != null) {
                File dir = new File(UPLOADED_FOLDER);
                for (int i = 0; i < multipartFiles.length; i++) {
                    MultipartFile file = multipartFiles[i];
                    System.out.println(" File name : " + file.getOriginalFilename());
                    System.out.println("Is file empty? : " + file.isEmpty());

                    try {
                        byte[] bytes = file.getBytes();

                        if (!dir.exists())
                            dir.mkdirs();

                        File uploadFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
                        outputStream.write(bytes);
                        tempFileNames.add(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                        outputStream.close();

                    } catch (FileNotFoundException fileNotFoundException) {
                        System.out.println("Image not Found");
                        modelMap.addAttribute("imageError", "Please upload a valid file");
                    } catch (IOException ioException) {
                        System.out.println("Error Occured while saving file");
                        modelMap.addAttribute("imageError", "There was a problem while saving file. Please try again");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }


            // Extracting multiple emails from toMail and sending mail to each user
            List<String> toMailList = Arrays.asList(mail.getToMail().split(","));


            for (String toMail : toMailList) {
                Mail tempMail = new Mail();
                if (mailService.searchMail(toMail)) {
                    tempMail.setToName(adminService.getAdminByEmail(toMail).getFirstName() + " " + adminService.getAdminByEmail(toMail).getLastName());
                } else {
                    tempMail.setToName(userService.getUserByEmail(toMail).getFirstName() + " " + userService.getUserByEmail(toMail).getLastName());
                }

                tempMail.setDate(formatted);
                tempMail.setFromName(admin.getFirstName() + " " + admin.getLastName());
                tempMail.setFromMail(fromMail);
                tempMail.setSubject(mail.getSubject());
                tempMail.setBody(mail.getBody());
                tempMail.setDocuments(tempFileNames);

                tempMail.setToMail(toMail);
                mailService.save(tempMail);
                System.out.println("Done");
            }

            return "redirect:/admin/compose";
        } else {
            return "redirect:/admin/login";
        }
    }

}
