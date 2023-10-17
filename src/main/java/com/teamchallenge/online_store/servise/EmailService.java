package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.EmailRequest;
import com.teamchallenge.online_store.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;




@Service
public class EmailService {

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public void sendEmail(EmailRequest emailRequest) {
        try {
            JsonNode jsonNode = sendSimpleMessage(emailRequest);
            System.out.println(jsonNode.toString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        emailRepository.save(emailRequest);
    }

    private JsonNode sendSimpleMessage(EmailRequest emailRequest) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", emailRequest.getToEmail())
                .queryString("to", "viktoriia.t@ukr.net")
                .queryString("subject", emailRequest.getSubject())
                .queryString("text", emailRequest.getText())
                .asJson();
        return request.getBody();
    }
}





