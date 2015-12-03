package org.keedio.examples.oxford;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("thumbnails")
public class Thumbnail extends OxfordService implements IService {

    @Value("${endpoint}")
    private String endpoint;

    @Value("${subscriptionKey}")
    private String subscriptionKey;

    @Value("${width}")
    private Integer width;

    @Value("${height}")
    private Integer height;

    @Value("${smartCropping}")
    private Boolean smartCropping;

    public Thumbnail() {
        super();
    }

    public Object request(String file) throws IOException {

        Map<String, Object> params = new HashMap<>();
        params.put("width", width);
        params.put("height", height);
        params.put("smartCropping", smartCropping);

        // FIXME: that's awful and doesn't work anyway...
        byte[] dummy = "".getBytes();
        Request request = new Request(endpoint, params);
        ResponseEntity<?> thumbnail = request.postFile(file, getHeaders(true), dummy.getClass());

        try {
            FileOutputStream output = new FileOutputStream(new File("thumbnail.jpg"));
            // Base64.decode(response.getBody().toString().getBytes(), output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Thumbnail saved to thumbnail.jpg";
    }

}
