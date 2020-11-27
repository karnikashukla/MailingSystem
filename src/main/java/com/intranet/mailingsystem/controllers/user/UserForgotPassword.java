package com.intranet.mailingsystem.controllers.user;

import com.intranet.mailingsystem.models.ForgotPassword;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;

@Controller
@RequestMapping("/users")
public class UserForgotPassword {
    @Autowired
    UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/forgot")
    public String ForgotPassword(ModelMap modelMap){
        String userForgot = "";
        modelMap.addAttribute("forgotPassword",new ForgotPassword());
        return "forgot_password";
    }

    @PostMapping("/forgot")
    public String sendForgetEmail(@ModelAttribute("forgotPassword") ForgotPassword forgotPassword, HttpSession session) throws MessagingException {
        System.out.println(" Frontend : " + forgotPassword.getEmail());
        String alternateEmail = userService.getUserByEmail(forgotPassword.getEmail()).getAlternateEmail();
        long userId = userService.getUserByEmail(forgotPassword.getEmail()).getId();
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        String formatted = String.format("%05d", num);

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(alternateEmail);
        helper.setFrom("karanbhatt512@gmail.com");
        helper.setSubject("Mailing System - Change password requested");
        String msg = "<html lang='en'><body><p>You are getting this email to change your password. </p> <br> OTP :"+ formatted +"<br> This OTP is valid for 30 minutes. <p>If you have not requested to change password, Ignore this email.</p><p>Regards,</p><p>Intranet Mailing System Team.</p></body></html>";

        //Context ctx = new Context();
        //final String htmlContent = this.templateEngine.process("change-password.html", ctx);

        mail.setContent(msg, "text/html");
        javaMailSender.send(mail);
        session.setAttribute("OTP", formatted);
        session.setAttribute("changeUserId", userId);
        return "redirect:/users/new/password";
    }
}
