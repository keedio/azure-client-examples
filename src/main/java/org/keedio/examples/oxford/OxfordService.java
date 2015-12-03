package org.keedio.examples.oxford;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

abstract class OxfordService {

    @Value("${endpoint}")
    protected String endpoint;

    @Value("${subscriptionKey}")
    protected String subscriptionKey;

    OxfordService() {
    }

    protected HttpHeaders getHeaders(boolean acceptBinary) {
        List<MediaType> mediaTypes = new ArrayList<>();
        if (acceptBinary) {
            mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        } else {
            mediaTypes.add(MediaType.APPLICATION_JSON);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);
        headers.setAccept(mediaTypes);

        return headers;
    }

}
