package org.keedio.examples.oxford;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("emotion")
public class EmotionScores extends OxfordService implements IService {

    @Value("${face.rectangles}")
    private String faceRectangles;

    public EmotionScores() {
        super();
    }

    public Object request(String file) throws IOException {

        Map<String,String> params = new HashMap<>();
        if (!faceRectangles.isEmpty()) {
            params.put("faceRectangle", faceRectangles);
        }

        ArrayList<Object> dummy = new ArrayList<>();
        Request request = new Request(endpoint, params);
        ResponseEntity<?> emotion = request.postFile(file, getHeaders(false), dummy.getClass());

        return emotion.getBody();
    }
}
