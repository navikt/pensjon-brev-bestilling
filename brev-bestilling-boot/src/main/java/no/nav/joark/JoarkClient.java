package no.nav.joark;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import no.nav.joarkintegrasjon.model.Dokument;
import no.nav.joarkintegrasjon.model.OpprettJournalpostRequest;
import no.nav.joarkintegrasjon.model.OpprettJournalpostResponse;

public class JoarkClient {
    private RestTemplate restTemplate;
    private String url;

    public JoarkClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }
    public String opprett(){
        Dokument dummyDokument = new Dokument("pensjon-brev-bestilling dummy tittel", "pensjon-brev-bestilling 123456", null);
        Dokument[] dokumenter = {dummyDokument};
        OpprettJournalpostRequest request = new OpprettJournalpostRequest(OpprettJournalpostRequest.JournalpostType.UTGAAENDE,  dokumenter, null, null, null, null, null, null, null, null);
        HashMap<String, String> uriVariabel = new HashMap<>();
        uriVariabel.put("forsoekFerdigstill", "false");
        ResponseEntity<OpprettJournalpostResponse> response = restTemplate.postForEntity(url, request, OpprettJournalpostResponse.class, uriVariabel);
        System.out.println("The response=================\n"+response);
        return response.getBody().getJournalpostId();
    }
}
