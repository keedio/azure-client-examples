package org.keedio.examples.oxford;

import org.springframework.http.HttpHeaders;

abstract class OxfordService {

    OxfordService() {}

    protected HttpHeaders getHeaders(String key) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", key);
        return headers;
    }
}
