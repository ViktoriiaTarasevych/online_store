package com.teamchallenge.online_store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EmailRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String toEmail;
    private String text;
    private String subject;

    public EmailRequest() {
    }

    public EmailRequest(String toEmail, String text, String subject) {
        this.toEmail = toEmail;
        this.text = text;
        this.subject = subject;
    }
}
