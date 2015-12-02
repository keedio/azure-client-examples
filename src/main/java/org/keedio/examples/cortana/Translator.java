package org.keedio.examples.cortana;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("translator")
public class Translator extends CortanaService implements IService {

    private static final Logger log = LoggerFactory.getLogger(Translator.class);

    @Value("${endpoint}")
    private String endpoint;
    @Value("${scope}")
    private String scope;
    @Value("${appID}")
    private String appID;
    @Value("${appSecret}")
    private String appSecret;

    public Translator() {
        super();
    }

    @Override
    public Object request(String text) throws IOException {

        String authToken = "Bearer " + getToken(appID, appSecret, scope);

        Map<String, String> params = new HashMap<>();
        params.put("appId", authToken);
        params.put("text", text);
        params.put("to", "en");
        params.put("contentType", "text/plain");

        return (new Request(endpoint, params)).get(String.class);
    }
}
