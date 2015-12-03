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
@Profile("visualfeatures")
public class VisualFeatures extends OxfordService implements IService {

    @Value("${visualFeatures}")
    private String visualFeatures;

    public VisualFeatures() {
        super();
    }

    public Object request(String file) throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put("visualFeatures", visualFeatures);

        HashMap<String, Object> dummy = new HashMap<>();
        Request request = new Request(endpoint, params);
        ResponseEntity<?> features = request.postFile(file, getHeaders(false), dummy.getClass());

        return features.getBody();
    }
}
