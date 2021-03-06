package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.models.User;
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
@RequestMapping("/users")
public class UserComposeController {
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @GetMapping("/compose")
    public String displayComposePage(HttpSession session, ModelMap modelMap, User user){
        if (session.getAttribute("userId") != null){
            user = userService.getUserById((long) session.getAttribute("userId"));
            modelMap.addAttribute("firstName", user.getFirstName());
            modelMap.addAttribute("lastName", user.getLastName());
            modelMap.addAttribute("fromMail",user.getEmail());
            if (session.getAttribute("toEmail") != null){
                modelMap.addAttribute("compose", new Mail(user.getEmail(), (String) session.getAttribute("toEmail")));
            }
            else {
                modelMap.addAttribute("compose", new Mail(user.getEmail()));
            }

            return "/compose";
        }
        else {
            return "redirect:/users/login";
        }


    }

    @RequestMapping(value = "/compose", consumes = {"multipart/form-data"},method = RequestMethod.POST)
    public String sendEmail(@Valid @ModelAttribute("compose") Mail mail, HttpSession session, ModelMap modelMap, @RequestParam(value = "file")MultipartFile[] multipartFiles) {

        long id = (long) session.getAttribute("userId");

        User user = userService.getUserById(id);
        String fromMail = user.getEmail();

        // Getting current System time
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String format = current.format(formatter);



        // Storing file in specific folder
        String formatted = format.replaceAll("[^\\w\\s]"," ");
        String UPLOADED_FOLDER = "E://Intranet/" + user.getId() + "/" + formatted;
        List<String> tempFileNames = new ArrayList<String>();
        if(multipartFiles!=null)
        {
            File dir = new File(UPLOADED_FOLDER);
            for (int i = 0; i < multipartFiles.length; i++) {
                MultipartFile file = multipartFiles[i];


                try {
                    byte[] bytes = file.getBytes();

                    if (!dir.exists())
                        dir.mkdirs();

                    File uploadFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
                    outputStream.write(bytes);
                    tempFileNames.add(dir.getAbsolutePath() + File.separator +  file.getOriginalFilename());
                    outputStream.close();

                } catch (FileNotFoundException fileNotFoundException) {
                    System.out.println("Image not Found");
                    modelMap.addAttribute("imageNotFoundError", "Please upload a valid file");
                } catch (IOException ioException) {
                    System.out.println("Error Occured while saving file");
                    modelMap.addAttribute("addImageError", "There was a problem while saving file. Please try again");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        else
        {
            tempFileNames=null;
        }


        // Extracting multiple emails from toMail and sending mail to each user
        List<String> toMailList = Arrays.asList(mail.getToMail().split(","));
        String domain = fromMail.substring(fromMail.indexOf("@") + 1);
        Pattern emailFinder = Pattern.compile(".*" + domain);


        for(String toMail: toMailList){
            Matcher toMailMatcher = emailFinder.matcher(toMail);
            Matcher fromMailMatcher = emailFinder.matcher(fromMail);
            System.out.println(" From Mail ::: " + fromMailMatcher.toString() + " To Mail ::: " + toMailMatcher.toString() );
            if(toMailMatcher.find() && fromMailMatcher.find()){
                if(userService.getUserByEmail(toMail) != null){
                    Mail tempMail = new Mail();
                    tempMail.setDate(format);
                    tempMail.setToName(userService.getUserByEmail(toMail).getFirstName() + " " + userService.getUserByEmail(toMail).getLastName());
                    tempMail.setFromName(user.getFirstName() + " " + user.getLastName());
                    tempMail.setFromMail(fromMail);
                    tempMail.setSubject(mail.getSubject());
                    tempMail.setBody(mail.getBody());
                    tempMail.setDocuments(tempFileNames);

                    tempMail.setToMail(toMail);
                    mailService.save(tempMail);
                    System.out.println("Done");
                }
                else {
                    System.out.println("User does not exist");
                }

            }
            else {
                System.out.println(toMail + " ::: This user does not belong to ");
            }

        }

        return "redirect:/users/compose";
    }
}
