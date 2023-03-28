package com.example.sprintToDo.demo.service.email;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Email;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.net.URI;
import java.net.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;

@PropertySource("classpath:application.properties")
public class EmailService {
    /**
     * This call sends an email to one recipient.
     */
    Dotenv dotenv = Dotenv.load();

    public void sendEmail(String sendEmail) {
        final String API_KEY = dotenv.get("API_KEY");
        final String API_SECRET = dotenv.get("API_SECRET");
        final String FROM_EMAIL = dotenv.get("FROM_EMAIL");
        final String API_BASE_URL = dotenv.get("API_BASE_URL");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(API_KEY, API_SECRET);
        String url = API_BASE_URL + "/send";
        String jsonBody = "{\"FromEmail\":\"" + FROM_EMAIL + "\",\"FromName\":\"TODO APP JAVA\",\"Subject\":\"Doğrulama Maili!\",\"Text-part\":\"mailinizi doğrulayın lütfen\",\"Html-part\":\"<h3>Hesabınızı doğrulamak için linkte tıklayınız: <a>link</a></h3>\",\"To\":\"Name <"+sendEmail+">\",\"CC\":\"\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    public void sendExcelEmail(String sendEmail , String fileUrl) {
        final String API_KEY = dotenv.get("API_KEY");
        final String API_SECRET = dotenv.get("API_SECRET");
        final String FROM_EMAIL = dotenv.get("FROM_EMAIL");
        final String API_BASE_URL = dotenv.get("API_BASE_URL");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(API_KEY, API_SECRET);
        String url = API_BASE_URL + "/send";
        String jsonBody = "{\"FromEmail\":\"" + FROM_EMAIL + "\",\"FromName\":\"TODO APP JAVA\",\"Subject\":\"Excel indirme maili!\",\"Text-part\":\"Dosyanızı indirin lütfen\",\"Html-part\":\"<h3>Dosyanızı indirek için tıklayınız: <a href="+fileUrl+">İndirme Linki</a></h3>\",\"To\":\"Name <"+sendEmail+">\",\"CC\":\"\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }


}
