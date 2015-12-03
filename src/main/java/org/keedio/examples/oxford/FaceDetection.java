package org.keedio.examples.oxford;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("facedetection")
public class FaceDetection extends OxfordService implements IService {

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

        Map<String, Boolean> params = new HashMap<>();
        params.put("analyzesFaceLandmarks", analyzesFaceLandmarks);
        params.put("analyzesAge", analyzesAge);
        params.put("analyzesGender", analyzesGender);
        params.put("analyzesHeadPose", analyzesHeadPose);

        ArrayList<Object> dummy = new ArrayList<>();
        Request request = new Request(endpoint, params);
        ResponseEntity<?> detection = request.exchange(file, getHeaders(false), dummy.getClass());

        return detection.getBody();
    }
}
