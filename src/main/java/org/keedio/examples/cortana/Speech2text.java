package org.keedio.examples.cortana;

import org.apache.commons.io.IOUtils;
import org.keedio.examples.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
@Profile("speech2text")
public class Speech2text extends CortanaService implements IService {

    private static final Logger log = LoggerFactory.getLogger(Speech2text.class);

    @Value("${scope}")     private String scope;
    @Value("${appID}")     private String appID;
    @Value("${appSecret}") private String appSecret;
    @Value("${deviceID}")  private String deviceID;

    Speech2text() {
        super();
    }

    /**
     * Call the conversion API.
     * <p/>
     * Check out MS documentation at:
     * https://onedrive.live.com/view.aspx?resid=9A8C02C3B59E575!106&ithint=file%2cdocx&app=Word&authkey=!AIEJaNeh8CcDTjU
     * <p/>
     * (you need an Azure account to acces the above file).
     * <p/>
     * Relies on the existence of a BING_AUTH environment variable to verify the current device has been properly authenticated.
     *
     * @return a {@see Conversion} object encapsulating the info returned by the Microsoft API.
     * @throws IOException
     */
    public HashMap<String, String> request(String file) throws IOException {
        String authToken = "Bearer " + getToken(appID, appSecret, scope);

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_XML);
        mediaTypes.add(MediaType.APPLICATION_JSON);

        headers.setAccept(mediaTypes);

        headers.add(HttpHeaders.AUTHORIZATION, authToken);
        headers.add(HttpHeaders.HOST, "speech.platform.bing.com");
        headers.set(HttpHeaders.CONTENT_TYPE, "audio/wav;codec=\"audio/pcm\";samplerate=8000;trustsourcerate=false");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<byte[]> entity = new HttpEntity<>(IOUtils.toByteArray(new FileInputStream(file)), headers);

        String requestId = UUID.randomUUID().toString();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://speech.platform.bing.com/recognize/query")
                .queryParam("format", "json")
                .queryParam("appid", appID)
                .queryParam("locale", "en-US")
                .queryParam("device.os", "Linux")
                .queryParam("version", "3.0")
                .queryParam("maxnbest", "3")
                .queryParam("scenarios", "smd")
                .queryParam("instanceid", deviceID)
                .queryParam("requestid", requestId);

        HashMap<String, String> dummy = new HashMap<>();

        ResponseEntity<HashMap<String, String>> conversion =
                restTemplate.exchange(builder.build().encode().toUri(),
                        HttpMethod.POST, entity, (Class<HashMap<String, String>>) dummy.getClass());

        return conversion.getBody();
    }

}
