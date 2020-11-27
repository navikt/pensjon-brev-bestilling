package no.nav.joark;

import no.nav.joarkintegrasjon.model.OpprettJournalpostRequest;
import no.nav.joarkintegrasjon.model.OpprettJournalpostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class JoarkClient {
    private RestTemplate restTemplate;
    private String url;

    public JoarkClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }
    public String opprett(){
        OpprettJournalpostRequest request = new OpprettJournalpostRequest(OpprettJournalpostRequest.JournalpostType.uTGAAENDE, null, null, null, null, null, null, null, null, null);
        HashMap<String, String> uriVariabel = new HashMap<>();
        uriVariabel.put("forsoekFerdigstill", "false");
        ResponseEntity<OpprettJournalpostResponse> response = restTemplate.postForEntity(url + "/journalpost", request, OpprettJournalpostResponse.class, uriVariabel);
        System.out.println("The response=================\n"+response);
        return response.getBody().getJournalpostId();
    }
}
