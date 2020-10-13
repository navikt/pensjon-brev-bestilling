package no.nav.pensjon.brev.bestilling.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoint {

	@Autowired
	StsRestClient client;
	
	@GetMapping("/sayHelloSts")
	public String sayHello() {
		return client.getToken();
	}
	
}
