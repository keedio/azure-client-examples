package org.keedio.examples.domain;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AuthService {

    private static final String DATAMARKET_ACCESS_URI = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";

    /**
     * Authentication helper method.
     *
     * @throws UnsupportedEncodingException
     */
    public static String token(String appID, String appSecret, String scope) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();

        String body =
                String.format("grant_type=client_credentials&client_id=%s&client_secret=%s&scope=%s",
                        URLEncoder.encode(appID, "UTF-8"),
                        URLEncoder.encode(appSecret, "UTF-8"),
                        URLEncoder.encode(scope, "UTF-8"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<AdmAccessToken> result =
                restTemplate.exchange(DATAMARKET_ACCESS_URI, HttpMethod.POST, entity, AdmAccessToken.class);

        return result.getBody().getAccess_token();
    }
}
