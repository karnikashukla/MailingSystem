package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.Corporation;
import com.intranet.mailingsystem.repositories.CorporationRepository;
import com.intranet.mailingsystem.service.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminAddCorporationController {

    @Autowired
    CorporationService corporationService;

    @GetMapping("/add/corporation")
    public String displayAddCorporationsPage(ModelMap modelMap){
        modelMap.addAttribute("newCorporation", new Corporation());
        return "forms";
    }

    @PostMapping("/add/corporation")
    public String addCorporation(ModelMap modelMap, @ModelAttribute("newCorporation") Corporation corporation){
        corporationService.save(corporation);
        return "redirect:/admin/add/corporation";
    }
}
