package org.keedio.examples.oxford;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("spellcheck")
public class SpellCheck extends OxfordService implements IService {

    @Value("${mode}")
    private String mode;

    public SpellCheck() {
        super();
    }

    public Object request(String text) throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put("mode", mode);

        String body = "Text=" + text;

        Request request = new Request(endpoint, params);
        return request.postText(body, getHeaders(false), String.class);

    }

}
