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
	
	@Value("#{environment.STS_API_KEY}")
	private String apiKey="";
	
	
	@Value("#{environment.JOARK_API_KEY_Q1}")
	private String apiKeyJOARKQ1="";
	
	private RestTemplate restTemplate;
	
	private static String API_GW_Q1 = "https://api-gw-q1.oera.no";
	
	private static String JOARK_SERVICE = API_GW_Q1 + "/dokarkiv/rest/journalpostapi/v1/journalpost";
	
	@Autowired
	public TestController(RestTemplateBuilder builder) {
		
		//builder.basicAuthentication(username, password);
		builder.defaultHeader("x-nav-apiKey", apiKey);
		
		restTemplate = builder.build();
	}

	@GetMapping("/sayhello")
	public String hello () {
		String message ="";
		try {
		
			HttpHeaders headers = new HttpHeaders();
			headers.set("x-nav-apiKey", apiKey);
			headers.setBasicAuth(username,password);
			HttpEntity entity = new HttpEntity(headers);
			ResponseEntity<String> rentity = restTemplate.postForEntity(API_GW_Q1 + "/security-token-service/rest/v1/sts/token?grant_type=client_credentials&scope=openid",entity ,String.class);
			message = rentity.getBody();
		}
		catch(HttpStatusCodeException e) {
			System.out.println(e.getResponseBodyAsString());
			e.printStackTrace();
		}
		
		return "Hello! u" + username.length() +" p" + password.length() +" k" + apiKey.length() + " " + message;
	}
	
	@PostMapping("/sayhello2")
	public String hello (@RequestBody String msg) {
		String message ="";
		try {
		
			HttpHeaders headers = new HttpHeaders();
			headers.set("x-nav-apiKey", apiKeyJOARKQ1);
			headers.setBearerAuth(msg);
			HttpEntity entity = new HttpEntity(headers);
			ResponseEntity<String> rentity = restTemplate.exchange(JOARK_SERVICE, HttpMethod.POST, entity, String.class);
			message = rentity.getBody();
		}
		catch(HttpStatusCodeException e) {
			System.out.println(e.getResponseBodyAsString());
			e.printStackTrace();
		}
		
		return "Hello! u" + username.length() +" p" + password.length() +" k" + apiKeyJOARKQ1.length()  + message;
	}
	
	
	
	
	
}
