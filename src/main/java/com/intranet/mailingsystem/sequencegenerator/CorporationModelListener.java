package com.intranet.mailingsystem.sequencegenerator;

import com.intranet.mailingsystem.models.Corporation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class CorporationModelListener extends AbstractMongoEventListener<Corporation> {
    private  SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public CorporationModelListener(SequenceGeneratorService sequenceGeneratorService){
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Corporation> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Corporation.SEQUENCE_NAME));
        }
    }
}
