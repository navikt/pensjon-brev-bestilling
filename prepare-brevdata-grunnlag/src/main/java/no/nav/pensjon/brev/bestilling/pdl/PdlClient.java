package no.nav.pensjon.brev.bestilling.pdl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PdlClient {

    private RestTemplate restTemplate;
    private String pdlApiKey;
    private String pdlUrl;

    public PdlClient(RestTemplate restTemplate, String pdlApiKey, String pdlUrl) {
        this.restTemplate = restTemplate;
        this.pdlApiKey = pdlApiKey;
        this.pdlUrl = pdlUrl;
    }

    public void query() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-nav-apiKey", pdlApiKey);
        headers.add("Tema", "PEN");
        headers.add("content-type", "application/json");

        // query is a grapql query wrapped into a String
        String query1 = "{\"query\":\"query{\\n  hentIdenter(ident: 12345678910, grupper: FOLKEREGISTERIDENT, historikk: false){\\n    identer {\\n      ident\\n    }\\n  "
                + "}\\n}\",\"variables\":{}}";

        ResponseEntity<String> response = restTemplate.postForEntity(pdlUrl, new HttpEntity<>(query1, headers), String.class);
        System.out.println("The response=================\n"+response);
    }
}
