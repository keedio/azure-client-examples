package org.keedio.examples.cortana;

import org.apache.commons.codec.binary.Base64;
import org.keedio.examples.domain.AuthService;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

abstract class CortanaService {

    private Boolean isTokenSet;
    private String token;

    @Value("${endpoint}")
    protected String endpoint;

    @Value("${accountKey}")
    protected String accountKey;

    @Value("${scope:#{null}}")
    protected String scope;

    @Value("${appID:#{null}}")
    protected String appID;

    @Value("${appSecret:#{null}}")
    protected String appSecret;

    CortanaService() {
        this.isTokenSet = false;
    }

    /**
     * Microsoft service needs to app ID, app Secret and scope of the service
     * to deliver the corresponding token. These can be found at your Microsoft
     * Azure Marketplace <a href="https://datamarket.azure.com/developer/applications">developer account</a>.
     *
     * @return token
     */
    protected String getToken() {
        if (!isTokenSet) {
            try {
                this.token = AuthService.token(appID, appSecret, scope);
                this.isTokenSet = true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    /**
     * Credentials are used for basic authentication. There are build with
     * the Microsoft Azure Marketplace <a href="https://datamarket.azure.com/account/keys">account key</a>.
     *
     * @return credentials
     */
    protected String getCredentials() {
        String auth = "AccountKey:" + accountKey;
        return Base64.encodeBase64String(auth.getBytes());
    }
}
