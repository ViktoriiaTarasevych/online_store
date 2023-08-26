package com.teamchallenge.online_store.servise;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import com.teamchallenge.online_store.errors.MailjetSocketTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mailjet.client.ClientOptions;


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
                                                    .put("Text", text)))
                                    .put(Emailv31.Message.TEXTPART, "Перевірка")));

            MailjetResponse response = client.post(request);
            if (response.getStatus() != 200) {
                System.err.println("Error sending email. Mailjet response: " + response.getData());
            }
        } catch (MailjetException e) {
            System.err.println("Mailjet error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void send2(String toEmail, String text) throws MailjetException, MailjetSocketTimeoutException {

        MailjetRequest request;
        MailjetResponse response;
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "vikka1836@gmail.com")
                                        .put("Name", "Mailjet Pilot"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)
                                                .put("Text", text)))
                                .put(Emailv31.Message.SUBJECT, "Your email flight plan!")
                                .put(Emailv31.Message.TEXTPART, "Dear passenger 1, welcome to Mailjet! May the delivery force be with you!")
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href=\"https://www.mailjet.com/\">Mailjet</a>!</h3><br />May the delivery force be with you!")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }


    public void send3(String toEmail, String text) throws MailjetException, MailjetSocketTimeoutException {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "vikka1836@gmail.com")
                                        .put("Name", "Mailjet Pilot"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)
                                                .put("Text", text)))
                                .put(Emailv31.Message.SUBJECT, "Your email flight plan!")
                                .put(Emailv31.Message.TEXTPART, "Dear passenger 1, welcome to Mailjet! May the delivery force be with you!")
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href=\"https://www.mailjet.com/\">Mailjet</a>!</h3><br />May the delivery force be with you!")));
        MailjetResponse response = client.post(request);

        if (response.getStatus() != 200) {
            System.err.println("Error sending email. Mailjet response: " + response.getData());
        }
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



