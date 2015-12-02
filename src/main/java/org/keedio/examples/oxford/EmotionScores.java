package org.keedio.examples.oxford;

import org.apache.commons.io.IOUtils;
import org.keedio.examples.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("emotion")
public class EmotionScores extends OxfordService implements IService {

    private static final Logger log = LoggerFactory.getLogger(EmotionScores.class);

    @Value("${endpoint}")
    private String endpoint;

    @Value("${subscriptionKey}")
    private String subscriptionKey;

    @Value("${face.rectangles}")
    private String faceRectangles;


    public EmotionScores() {
        super();
    }

    public String request(String file) throws IOException {

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = getHeaders(subscriptionKey);
        headers.set(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.setAccept(mediaTypes);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint);
        if (!faceRectangles.isEmpty()) {
            builder.queryParam("faceRectangle", faceRectangles);
        }

        URI query = builder.build().encode().toUri();
        log.info("URI is: " + query.toString());

        HttpEntity<byte[]> entity = new HttpEntity<>(IOUtils.toByteArray(new FileInputStream(file)), headers);

        ArrayList<Object> dummy = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<Object>> detection =
                restTemplate.exchange(query, HttpMethod.POST, entity, (Class<ArrayList<Object>>) dummy.getClass());

        return detection.getBody().toString();
    }
}
