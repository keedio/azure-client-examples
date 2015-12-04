package org.keedio.examples.cortana;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("textanalytics")
public class TextAnalytics extends CortanaService implements IService {

    @Value("${service}")
    private String service;

    private static final Map<String, String> svc2method;
    static {
        svc2method = new HashMap<String, String>();
        svc2method.put("sentiment", "GetSentiment");
        svc2method.put("keyphrases", "GetKeyPhrases");
        svc2method.put("language", "GetLanguage");
    }

    public TextAnalytics() {
        super();
    }

    @Override
    public Object request(String text) throws IOException {

        String url = endpoint + "/" + svc2method.get(service);

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + getCredentials());
        headers.setAccept(mediaTypes);

        Map<String, String> params = new HashMap<>();
        params.put("Text", text);

        Request request = new Request(url, params);
        ResponseEntity<?> sentiment = request.get(String.class, headers);

        return sentiment.getBody();

    }
}
