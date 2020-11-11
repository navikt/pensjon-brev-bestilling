package no.nav.pensjon.brev.bestilling.pesys;

import org.openapitools.client.models.VedtakDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class VedtakClient {
    private RestTemplate restTemplate;
    private String url;

    public VedtakClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public VedtakDto vedtak(Integer vedtakId) {
        ResponseEntity<VedtakDto> response = restTemplate.getForEntity(url+vedtakId+"?fetchSet=ENTITY_ONLY", VedtakDto.class);
        System.out.println("The response=================\n"+response);
        return response.getBody();
    }
}
