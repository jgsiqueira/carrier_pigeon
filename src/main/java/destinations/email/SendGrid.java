package destinations.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import java.io.IOException;

public class SendGrid extends Email {
    private String apiKey;

    public SendGrid(String from, String to, String subject, String apiKey) {
        super(from, to, subject);
        this.apiKey = apiKey;
    }

    @Override
    public boolean sendMessage(String message) {
        com.sendgrid.helpers.mail.objects.Email from = new com.sendgrid.helpers.mail.objects.Email(getFrom());
        String subject = getSubject();
        com.sendgrid.helpers.mail.objects.Email to = new com.sendgrid.helpers.mail.objects.Email(getTo());
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);
        com.sendgrid.SendGrid sg = new com.sendgrid.SendGrid(apiKey);

        Request request = new Request();
        boolean result;

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            result = true;
        } catch (IOException ex) {
            result = false;
        }

        return result;
    }
}
