package org.keedio.examples.cortana;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("translator")
public class Translator extends CortanaService implements IService {

    public Translator() {
        super();
    }

    @Override
    public Object request(String text) throws IOException {

        String authToken = "Bearer " + getToken();

        Map<String, String> params = new HashMap<>();
        params.put("appId", authToken);
        params.put("text", text);
        params.put("to", "en");
        params.put("contentType", "text/plain");

        return (new Request(endpoint, params)).get(String.class);
    }
}
