package com.intranet.mailingsystem.rest;

import com.intranet.mailingsystem.service.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
public class CorporationRestAPI {

    @Autowired
    CorporationService corporationService;

    @RequestMapping(value = "/corporations", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
    public Response getAllCorporations(){
        System.out.println("::: In get all countries RestAPI :::");
        return Response
                .status(200)
                .entity(corporationService.findAllCorporation())
                .build();
    }
}
