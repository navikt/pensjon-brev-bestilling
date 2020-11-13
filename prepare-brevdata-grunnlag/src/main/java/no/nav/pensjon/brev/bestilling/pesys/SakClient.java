package no.nav.pensjon.brev.bestilling.pesys;

import org.openapitools.client.models.HentSakResponseDto;
import org.openapitools.client.models.SakDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SakClient {
    private RestTemplate restTemplate;
    private String url;

    public SakClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public SakDto sak(Integer sakId) {
        ResponseEntity<HentSakResponseDto> response = restTemplate.getForEntity(url + sakId + "/hentsak", HentSakResponseDto.class);
        System.out.println("The response=================\n"+response);
        return response.getBody().getSak();
    }
}
