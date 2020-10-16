package com.intranet.mailingsystem.sequencegenerator;

import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class MailModelListener extends AbstractMongoEventListener<Mail> {
    private  SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public MailModelListener(SequenceGeneratorService sequenceGeneratorService){
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Mail> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Mail.SEQUENCE_NAME));
        }
    }
}
