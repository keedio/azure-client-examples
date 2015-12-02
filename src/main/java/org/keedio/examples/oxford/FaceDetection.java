package org.keedio.examples.oxford;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Object request(String file) throws IOException {

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        HttpHeaders headers = getHeaders(subscriptionKey);
        headers.setAccept(mediaTypes);

        Map<String, Boolean> params = new HashMap<>();
        params.put("analyzesFaceLandmarks", analyzesFaceLandmarks);
        params.put("analyzesAge", analyzesAge);
        params.put("analyzesGender", analyzesGender);
        params.put("analyzesHeadPose", analyzesHeadPose);

        ArrayList<Object> dummy = new ArrayList<>();
        ResponseEntity<?> detection = (new Request(endpoint, params)).exchange(file, headers, dummy.getClass());

        return detection.getBody();
    }
}
