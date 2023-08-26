package com.teamchallenge.online_store.controller;


import com.mailjet.client.errors.MailjetException;
import com.teamchallenge.online_store.errors.MailjetSocketTimeoutException;
import com.teamchallenge.online_store.model.EmailRequest;
import com.teamchallenge.online_store.servise.EmailService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@Tag(name = "Email")
public class EmailController {

    private final EmailService mailjetService;


    public EmailController(EmailService mailjetService) {
        this.mailjetService = mailjetService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            mailjetService.sendEmail(request.getToEmail(), request.getText());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MailjetException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }

    @PostMapping("/send2")
    public ResponseEntity<String> send2 (@RequestBody EmailRequest request) {
        try {
            mailjetService.send2(request.getToEmail(), request.getText());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }


    @PostMapping("/send3")
    public ResponseEntity<String> send3 (@RequestBody EmailRequest request) {
        try {
            mailjetService.send3(request.getToEmail(), request.getText());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }
}

