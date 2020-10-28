package com.intranet.mailingsystem.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "mail_collection")
public class Mail {
    @Transient
    public static final String SEQUENCE_NAME = "mail_sequence";

    @Id
    private long id;

    private String toName;
    private String toMail;
    private String fromName;
    private String fromMail;
    private String subject;
    private String body;
    private List<String> documents;
    private String Date;

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public long getId() {
        return id;
    }

    public void setId(long mailId) {
        this.id = mailId;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Mail() {
    }

    public Mail(String fromMail) {
        this.fromMail = fromMail;
    }

    public Mail(String fromMail, String toMail){
        this.fromMail = fromMail;
        this.toMail = toMail;
    }
}
