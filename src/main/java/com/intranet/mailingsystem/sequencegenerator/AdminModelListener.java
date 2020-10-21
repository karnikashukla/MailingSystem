package com.intranet.mailingsystem.sequencegenerator;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.models.Corporation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class AdminModelListener extends AbstractMongoEventListener<Admin> {
    private  SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public AdminModelListener(SequenceGeneratorService sequenceGeneratorService){
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Admin> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Admin.SEQUENCE_NAME));
        }
    }
}
