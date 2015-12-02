package org.keedio.examples.oxford;

import org.apache.commons.io.IOUtils;
import org.keedio.examples.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaceDetection implements IService {

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

    public HashMap<String, String> request(String file) throws IOException {

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);
        headers.setAccept(mediaTypes);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint)
                .queryParam("analyzesFaceLandmarks", analyzesFaceLandmarks)
                .queryParam("analyzesAge", analyzesAge)
                .queryParam("analyzesGender", analyzesGender)
                .queryParam("analyzesHeadPose", analyzesHeadPose);
        URI query = builder.build().encode().toUri();

        HttpEntity<byte[]> entity = new HttpEntity<>(IOUtils.toByteArray(new FileInputStream(file)), headers);

        HashMap<String, String> dummy = new HashMap<>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap<String, String>> detection =
                restTemplate.exchange(query, HttpMethod.POST, entity, (Class<HashMap<String, String>>) dummy.getClass());

        return detection.getBody();
    }
}
