package org.keedio.examples.rest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class Request {

    private static final Logger log = LoggerFactory.getLogger(Request.class);

    private URI query;
    private RestTemplate restTemplate;

    public Request(String endpoint, Map<String, ?> params) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint);

        for (Map.Entry<String, ?> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        this.query = builder.build().encode().toUri();
        this.restTemplate = new RestTemplate();

        log.info("URI is: " + query.toString());
    }

    // TODO: find a better way to wrap these
    public ResponseEntity<?> postFile(String file, HttpHeaders headers, Class<?> clazz)
            throws IOException {

        if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
            headers.set(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        }
        HttpEntity<byte[]> entity = new HttpEntity<>(IOUtils.toByteArray(new FileInputStream(file)), headers);

        return restTemplate.exchange(query, HttpMethod.POST, entity, clazz);
    }

    public ResponseEntity<?> postText(String body, HttpHeaders headers, Class<?> clazz) {

        if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
            headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        }
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(query, HttpMethod.POST, entity, clazz);
    }

    public Object get(Class<?> clazz) {
        return restTemplate.getForObject(query, clazz);
    }
}
