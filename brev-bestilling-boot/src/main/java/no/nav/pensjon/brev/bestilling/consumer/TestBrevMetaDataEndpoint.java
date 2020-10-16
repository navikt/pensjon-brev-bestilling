package no.nav.pensjon.brev.bestilling.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestBrevMetaDataEndpoint {

	@Autowired
    private BrevMetaData client;
	
	@GetMapping("/brevmetadata")
	public List brevMetaData() throws Exception {
		return client.hentAlleBrev(false);
	}
	
}
