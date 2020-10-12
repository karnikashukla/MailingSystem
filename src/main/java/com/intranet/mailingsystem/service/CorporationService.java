package com.intranet.mailingsystem.service;

import com.intranet.mailingsystem.models.Corporation;
import com.intranet.mailingsystem.repositories.CorporationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorporationService {
    @Autowired
    CorporationRepository corporationRepository;

    public void save(Corporation corporation){
        corporationRepository.insert(corporation);
    }
}
