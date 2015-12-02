package org.keedio.examples.cortana;

import org.keedio.examples.domain.AuthService;

import java.io.UnsupportedEncodingException;

abstract class CortanaService {

    private Boolean isTokenSet;
    private String token;

    CortanaService() {
        this.isTokenSet = false;
    }

    protected String getToken(String id, String secret, String url) {
        if (!isTokenSet) {
            try {
                this.token = AuthService.token(id, secret, url);
                this.isTokenSet = true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return token;
    }
}
