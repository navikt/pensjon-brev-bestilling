package no.nav.pensjon.brev.bestilling.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BrevMetaData {
    private static final Logger LOG = LoggerFactory.getLogger(BrevMetaData.class);

    @Value("${brevmetadata.client.url}")
    private String endpoint;

    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, String>> hentAlleBrev(boolean includeXsd) throws Exception {
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                    UriComponentsBuilder.fromHttpUrl(endpoint + "/allBrev/?includeXsd=" + includeXsd).toUriString(),
                    HttpMethod.GET,
                    null,
                    String.class);

            return objectMapper.readValue(response.getBody(), List.class);
        } catch (IOException | HttpStatusCodeException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

}
