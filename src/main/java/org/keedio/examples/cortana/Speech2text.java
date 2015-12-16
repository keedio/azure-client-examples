package org.keedio.examples.cortana;

import org.keedio.examples.IService;
import org.keedio.examples.rest.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@Profile("speech2text")
public class Speech2text extends CortanaService implements IService {

    @Value("${deviceID}")
    private String deviceID;

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
    public Object request(String file) throws IOException {
        String authToken = "Bearer " + getToken();

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authToken);
        headers.setAccept(mediaTypes);
        headers.set(HttpHeaders.CONTENT_TYPE, "audio/wav;codec=\"audio/pcm\";samplerate=8000;trustsourcerate=false");

        Map<String, String> params = new HashMap<>();
        params.put("format", "json");
        params.put("appid", appID);
        params.put("locale", "en-US");
        params.put("device.os", "Linux");
        params.put("version", "3.0");
        params.put("maxnbest", "3");
        params.put("scenarios", "smd");
        params.put("instanceid", deviceID);
        params.put("requestid", UUID.randomUUID().toString());

        HashMap<String, String> dummy = new HashMap<>();

        ResponseEntity<?> conversion = (new Request(endpoint, params)).postFile(file, headers, dummy.getClass());

        return conversion.getBody();
    }

}
