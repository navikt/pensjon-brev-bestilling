package no.nav.pensjon.sts.client;

import java.util.function.Supplier;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class StsRestClient {
	
	private static final String X_NAV_APIKEY_NAME = "x-nav-apiKey";
	
	private final RestTemplate stsRestTemplate;
	
	
	public StsRestClient(StsConfigurationProperties configuration, RestTemplateBuilder builder,Supplier<ClientHttpRequestFactory> clientHttpFactorySupplier) {
		
		 
			builder = builder.rootUri(configuration.getUrl())
						     .basicAuthentication(configuration.getUsername(), configuration.getPassword())
						     .defaultHeader(X_NAV_APIKEY_NAME, configuration.getApikey());
	 	    stsRestTemplate = clientHttpFactorySupplier != null ? builder.requestFactory(clientHttpFactorySupplier).build() 
	 	    		                                            : builder.build();
								
	}


	public String getToken() {
		return stsRestTemplate.postForObject("/token?grant_type=client_credentials&scope=openid", null, StsResponse.class).getAccessToken();
	}
	
	


}
