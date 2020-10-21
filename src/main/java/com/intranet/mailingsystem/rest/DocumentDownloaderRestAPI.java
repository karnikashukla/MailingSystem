package com.intranet.mailingsystem.rest;


import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.MailService;
import com.intranet.mailingsystem.service.UserService;
import org.apache.tika.Tika;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.*;
import java.net.URLConnection;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
public class DocumentDownloaderRestAPI {
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    private static final Logger logger = LoggerFactory.getLogger(DocumentDownloaderRestAPI.class);

    @GetMapping("/download/{mailId}/{fileNo}")
    public ResponseEntity<InputStreamResource> downloadFileFromLocal(@PathVariable("mailId") int mailId, @PathVariable("fileNo") int fileNo, HttpServletResponse response, HttpSession session) throws IOException {
        User user = userService.getUserById((long) session.getAttribute("userId"));
        Mail mail = mailService.findMailById(mailId);
        String fileName = mail.getDocuments().get(fileNo);
        System.out.println(fileName);
        File file = new File(fileName);
        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        System.out.println(" Mime Type : " + mimeType);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.valueOf(mimeType)).contentLength(file.length())
                .body(resource);
    }
}
