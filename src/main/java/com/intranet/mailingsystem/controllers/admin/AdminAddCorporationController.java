package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.Corporation;
import com.intranet.mailingsystem.repositories.CorporationRepository;
import com.intranet.mailingsystem.sequencegenerator.SequenceGeneratorService;
import com.intranet.mailingsystem.service.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminAddCorporationController {

    @Autowired
    CorporationService corporationService;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/add/corporation")
    public String displayAddCorporationsPage(ModelMap modelMap, HttpSession session){
        if (session.getAttribute("adminId") != null)
        {
            modelMap.addAttribute("newCorporation", new Corporation());
            return "forms";
        }
        else {
            return "redirect:/admin/login";
        }

    }

    @GetMapping("domain/list")
    public String showDomains(HttpSession session){
        if (session.getAttribute("adminId") != null){
            return "admin-domainList";
        }
        else {
            return "redirect:/admin/login";
        }

    }

    @PostMapping("/add/corporation")
    public String addCorporation(ModelMap modelMap, @ModelAttribute("newCorporation") Corporation corporation, HttpSession session){
        if (session.getAttribute("adminId") != null){
            corporation.setId(sequenceGeneratorService.generateSequence(Corporation.SEQUENCE_NAME));
            corporationService.save(corporation);
            return "redirect:/admin/add/corporation";
        }
        else {
            return "redirect:/admin/login";
        }

    }
}
