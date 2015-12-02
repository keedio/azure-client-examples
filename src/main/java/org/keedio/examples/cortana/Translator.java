package org.keedio.examples.cortana;

import org.keedio.examples.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Component
@Profile("translator")
public class Translator extends CortanaService implements IService {

    private static final Logger log = LoggerFactory.getLogger(Translator.class);
    private static final String ENDPOINT = "http://api.microsofttranslator.com/V2/Http.svc/Translate";

    @Value("${scope}") private String scope;
    @Value("${appID}") private String appID;
    @Value("${appSecret}") private String appSecret;

    public Translator() {
        super();
    }

    @Override
    public String request(String text) throws IOException {

        String authToken = "Bearer " + getToken(appID, appSecret, scope);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .queryParam("appId", authToken)
                .queryParam("text", text)
                .queryParam("to", "en")
                .queryParam("contentType", "text/plain");

        RestTemplate restTemplate = new RestTemplate();
        URI query = builder.build().encode().toUri();
        log.info("uri es: " + query);

        return restTemplate.getForObject(query, String.class);
    }
}
