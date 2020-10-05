package no.nav.pensjon.brev.bestilling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
	
	@Value("#{environment.STS_API_KEY_Q1}")
	private String apiKey="";
	
	
	@Value("#{environment.JOARK_API_KEY_Q1}")
	private String apiKeyJOARKQ1="";
	
	private RestTemplate restTemplate;
	
	private static String API_GW_Q1 = "https://api-gw-q1.oera.no";
	
	private static String JOARK_SERVICE = API_GW_Q1 + "/dokarkiv/rest/journalpostapi/v1/journalpost?forsoekFerdigstill=false";
	
	
	private static String JOARK_REQUEST_BODY =  "{\r\n" + 
			"  \"tittel\": \"Søknad om dagpenger ved permittering\",\r\n" + 
			"  \"journalpostType\": \"INNGAAENDE\",\r\n" + 
			"  \"tema\": \"DAG\",\r\n" + 
			"  \"behandlingstema\": \"ab0001\",\r\n" + 
			"  \"kanal\": \"NAV_NO\",\r\n" + 
			"  \"journalfoerendeEnhet\": \"0701\",\r\n" + 
			"  \"avsenderMottaker\": {\r\n" + 
			"    \"id\": \"09071844797\",\r\n" + 
			"    \"idType\": \"FNR\",\r\n" + 
			"    \"navn\": \"Hansen, Per\"\r\n" + 
			"  },\r\n" + 
			"  \"bruker\": {\r\n" + 
			"    \"id\": \"09071844797\",\r\n" + 
			"    \"idType\": \"FNR\"\r\n" + 
			"  },\r\n" + 
			"  \"sak\": {\r\n" + 
			"    \"fagsakId\": \"10695768\",\r\n" + 
			"    \"fagsaksystem\": \"AO01\",\r\n" + 
			"    \"sakstype\": \"FAGSAK\"\r\n" + 
			"  },\r\n" + 
			"  \"dokumenter\": [\r\n" + 
			"    {\r\n" + 
			"      \"tittel\": \"Søknad om dagpenger ved permittering\",\r\n" + 
			"      \"brevkode\": \"NAV 04-01.04\",\r\n" + 
			"      \"dokumentvarianter\": [\r\n" + 
			"        {\r\n" + 
			"          \"filtype\": \"PDFA\",\r\n" + 
			"          \"fysiskDokument\": \"U8O4a25hZCBvbSBkYWdwZW5nZXIgdmVkIHBlcm1pdHRlcmluZw==\",\r\n" + 
			"          \"variantformat\": \"ARKIV\"\r\n" + 
			"        }\r\n" + 
			"      ]\r\n" + 
			"    }\r\n" + 
			"  ]\r\n" + 
			"}";
	
	@Autowired
	public TestController(RestTemplateBuilder builder) {
		
		//builder.basicAuthentication(username, password);
		builder.defaultHeader("x-nav-apiKey", apiKey);
		
		restTemplate = builder.build();
	}

	@GetMapping("/sayhellosts")
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
	
	@PostMapping("/sayhellojoark")
	public String hello (@RequestBody String msg) {
		String message ="";
		try {
		
			HttpHeaders headers = new HttpHeaders();
			headers.set("x-nav-apiKey", apiKeyJOARKQ1);
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(msg);
			HttpEntity entity = new HttpEntity(JOARK_REQUEST_BODY,headers);
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
