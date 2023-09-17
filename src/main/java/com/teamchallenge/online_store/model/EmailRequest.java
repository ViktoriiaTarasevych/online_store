package com.teamchallenge.online_store.model;

public class EmailRequest {

    private String toEmail;
    private String text;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EmailRequest(String toEmail, String text) {
        this.toEmail = toEmail;
        this.text = text;
    }

}
