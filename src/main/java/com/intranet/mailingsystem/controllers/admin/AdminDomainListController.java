package com.intranet.mailingsystem.controllers.admin;

import com.intranet.mailingsystem.fileconvertors.CorporationPDFExporter;
import com.intranet.mailingsystem.fileconvertors.UserPDFExporter;
import com.intranet.mailingsystem.models.Corporation;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.service.CorporationService;
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
public class AdminDomainListController {
    @Autowired
    CorporationService corporationService;

    @GetMapping("/corporations/list")
    public String showCorporations(ModelMap modelMap, HttpSession session){
        if (session.getAttribute("adminId") != null){
            modelMap.addAttribute("corporationList", corporationService.findAllCorporation());
            return "admin-domainList";
        }
        else {
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/corporations/list/download")
    public void exportCorporationsToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=corporation_list_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Corporation> corporationList = corporationService.findAllCorporation();

        CorporationPDFExporter exporter = new CorporationPDFExporter(corporationList);
        exporter.export(response);

    }

    @GetMapping("/corporations/delete/{corporationId}")
    public String deleteCorporation(@PathVariable("corporationId") long corporationId, ModelMap modelMap, HttpSession session){
        if (session.getAttribute("adminId") != null){
            corporationService.deleteCorporation(corporationId);
            modelMap.addAttribute("corporationDeletedSuccessfully", " Corporation Deleted Successfully");
            return "redirect:/admin/corporations/list";
        }
        else {
            return "redirect:/admin/login";
        }

    }
}
