package org.keedio.examples.domain;

import org.keedio.examples.rest.Request;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class AuthService {

    private static final String DATAMARKET_ACCESS_URI = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";

    /**
     * Authentication helper method.
     *
     * @throws UnsupportedEncodingException
     */
    public static String token(String appID, String appSecret, String scope) throws URISyntaxException, UnsupportedEncodingException {
        String body =
                String.format("grant_type=client_credentials&client_id=%s&client_secret=%s&scope=%s",
                        URLEncoder.encode(appID, "UTF-8"),
                        URLEncoder.encode(appSecret, "UTF-8"),
                        URLEncoder.encode(scope, "UTF-8"));

        Request request = new Request(DATAMARKET_ACCESS_URI);
        ResponseEntity<AdmAccessToken> result = (ResponseEntity<AdmAccessToken>) request.postText(body, AdmAccessToken.class);

        return result.getBody().getAccess_token();
    }
}
