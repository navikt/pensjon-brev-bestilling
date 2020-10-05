package no.nav.pensjon.brev.bestilling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
	
	@Value("#{environment.USERNAME_SRV_BRUKER_Q1}")
	private String username="";
	
	@Value("#{environment.PASSWORD_SRV_BRUKER_Q1}")
	private String password="";
	
	@Value("#{environment.PPG_KEY_Q1}")
	private String apiKeyVedtakQ4="";
	
	private RestTemplate restTemplate;
	
	private static String API_GW_Q1 = "https://api-gw-q1.oera.no";
	
	private static String VEDTAK_SERVICE = API_GW_Q1 + "/pensjon-fss/pensjon-ws/api/vedtak/vedtak/24364601?fetchSet=ENTITY_ONLY";
	
	@Autowired
	public TestController(RestTemplateBuilder builder) {
		
		//builder.basicAuthentication(username, password);
	//	builder.defaultHeader("x-nav-apiKey", apiKey);
		
		restTemplate = builder.build();
	}
	
	@GetMapping("/sayhello2")
	public String hello2 () {
		
		
		return "Hello!";
	}

	@PostMapping("/sayhellopesys")
	public String hello (@RequestBody String msg) {
		String message ="";
		try {
		
			HttpHeaders headers = new HttpHeaders();
			headers.set("x-nav-apiKey", apiKeyVedtakQ4);
			headers.setBearerAuth(msg);
			HttpEntity entity = new HttpEntity(headers);
			ResponseEntity<String> rentity = restTemplate.exchange(VEDTAK_SERVICE, HttpMethod.GET, entity, String.class);
			message = rentity.getBody();
		}
		catch(HttpStatusCodeException e) {
			System.out.println(e.getResponseBodyAsString());
			e.printStackTrace();
		}
		
		return "Hello! u" + username.length() +" p" + password.length() +" k" + apiKeyVedtakQ4.length()  + message;
	}
	
	
	
}
