package saransh13.me.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import saransh13.me.model.UserResponse;

import static saransh13.me.constants.constants.API_HEADER_KEY;

@Service
@Slf4j
public class GetEnrichedService {
    @Value("${api.key}")
    public String apiKey;

    @Value("${api.baseurl}")
    public String baseurl;

    public void modelUserToAdditionalDetails() {
        RestTemplate template = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.set(API_HEADER_KEY, apiKey);
        HttpEntity<Void> requestEntity = new HttpEntity<>(header);
        ResponseEntity<UserResponse> response = template.exchange
                (
                    baseurl + "/comment?limit=1",
                    HttpMethod.GET,
                    requestEntity,
                        UserResponse.class
                );
        log.info(response.toString());
        log.info(response.getBody().toString()) ;
//        return response.getBody();
    }

}
