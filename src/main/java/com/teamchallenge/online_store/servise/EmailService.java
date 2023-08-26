package com.teamchallenge.online_store.servise;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final MailjetClient client;


    @Value("${mailjet.api.key}")
    private String apiKey;

    @Value("${mailjet.secret.key}")
    private String secretKey;

    public EmailService() {
        client = new MailjetClient(apiKey, secretKey);
    }


    public void sendEmail(String toEmail, String text) throws MailjetException {
        try {
            MailjetRequest request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", "vikka1836@gmail.com")
                                            .put("Name", "PIDPAL"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", toEmail)
                                                    .put("Name", "Recipient Name")))
                                    .put(Emailv31.Message.TEXTPART, text)));

            MailjetResponse response = client.post(request);
            if (response.getStatus() != 200) {
                System.err.println("Error sending email. Mailjet response: " + response.getData());
            }
        } catch (MailjetException e) {
            System.err.println("Mailjet error: " + e.getMessage());
            e.printStackTrace();
        }
    }



//    public List<MailjetMessage> getMessages() throws MailjetException{
//        MailjetRequest request = new MailjetRequest(Message.resource, null, new ContactFilter().limit(10));
//        MailjetResponse response = client.get(request);
//
//        if (response.getStatus() == 200) {
//            JSONArray messagesArray = response.getData();
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(messagesArray.toString(), new TypeReference<List<MailjetMessage>>() {});
//        } else {
//            throw new RuntimeException("Failed to retrieve messages from Mailjet.");
//        }
//    }



//    public void createNewsletter(String subject, String content) {
//        // Логіка для створення розсилки через Mailjet API
//    }


}
