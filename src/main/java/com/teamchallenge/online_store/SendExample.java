package com.teamchallenge.online_store;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;
import com.mailjet.client.resource.Emailv31;
import com.teamchallenge.online_store.errors.MailjetSocketTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

public class SendExample {
    /**
     * This call sends a message to one recipient.
     */


    public static void main(String[] args) throws MailjetException, MailjetSocketTimeoutException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient(System.getenv("MJ_APIKEY_PUBLIC"), System.getenv("MJ_APIKEY_PRIVATE"));
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, "vikka1836@gmail.com")
                .property(Email.FROMNAME, "Mailjet Pilot")
                .property(Email.SUBJECT, "Your email flight plan!")
                .property(Email.TEXTPART, "Dear passenger, welcome to Mailjet! May the delivery force be with you!")
                .property(Email.HTMLPART, "<h3>Dear passenger, welcome to <a href=\"https://www.mailjet.com/\">Mailjet</a>!<br />May the delivery force be with you!")
                .property(Email.RECIPIENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("Email", "viktoriia.t@ukr.net")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}


