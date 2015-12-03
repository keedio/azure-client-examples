package org.keedio.examples.oxford;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("ocr")
public class CharRecognition extends OxfordService implements IService {

    @Value("${language}")
    private String language;

    @Value("${detectOrientation}")
    private String detectOrientation;

    public CharRecognition() {
        super();
    }

    public Object request(String file) throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put("detectOrientation", detectOrientation);
        if (!language.isEmpty()) {
            params.put("language", language);
        }

        HashMap<String, String> dummy = new HashMap<>();
        Request request = new Request(endpoint, params);
        ResponseEntity<?> text = request.exchange(file, getHeaders(false), dummy.getClass());

        return text.getBody();
    }

}
