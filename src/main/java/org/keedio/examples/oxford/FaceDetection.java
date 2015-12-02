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
@Profile("facedetection")
public class FaceDetection extends OxfordService implements IService {

    private static final Logger log = LoggerFactory.getLogger(FaceDetection.class);

    @Value("${endpoint}")
    private String endpoint;

    @Value("${subscriptionKey}")
    private String subscriptionKey;

    @Value("${analyzes.age}")
    private Boolean analyzesAge;

    @Value("${analyzes.gender}")
    private Boolean analyzesGender;

    @Value("${analyzes.faceLandmarks}")
    private Boolean analyzesFaceLandmarks;

    @Value("${analyzes.headPose}")
    private Boolean analyzesHeadPose;


    public FaceDetection() {
        super();
    }

    public String request(String file) throws IOException {

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = getHeaders(subscriptionKey);
        headers.set(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.setAccept(mediaTypes);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint)
                .queryParam("analyzesFaceLandmarks", analyzesFaceLandmarks)
                .queryParam("analyzesAge", analyzesAge)
                .queryParam("analyzesGender", analyzesGender)
                .queryParam("analyzesHeadPose", analyzesHeadPose);
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
