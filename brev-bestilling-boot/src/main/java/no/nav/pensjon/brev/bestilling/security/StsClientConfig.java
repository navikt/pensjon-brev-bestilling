package no.nav.pensjon.brev.bestilling.security;

import java.util.function.Supplier;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;



@Configuration
@EnableConfigurationProperties()
public class StsClientConfig extends StsConfigurationProperties{
	
		
	    
	    
	    @Bean
	    public StsRestClient stsRestTemplate(StsConfigurationProperties properties, RestTemplateBuilder builder,@Autowired(required = false)Supplier<ClientHttpRequestFactory> clientHttpFactorySupplier) {
	    	return new StsRestClient(properties,builder,clientHttpFactorySupplier);
	    }
	    
	    @Bean()
	    @Profile("vdi")
	    public Supplier<ClientHttpRequestFactory> dissablesSslCheckWhenInVDI() throws Exception {
	    	return () -> {
				
			 	TrustStrategy acceptingTrustStrategy = (java.security.cert.X509Certificate[] chain, String authType) -> true;
			 	
			 	SSLContext sslContext = null;
			 	
			 	try {
				     sslContext = org.apache.http.ssl.SSLContexts.custom()
				                    .loadTrustMaterial(null, acceptingTrustStrategy)
				                    .build();
			 	}
			 	catch(Exception e) {
					throw new RuntimeException(e);
				}
			 	

			    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			    CloseableHttpClient httpClient = HttpClients.custom()
			                    .setSSLSocketFactory(csf)
			                    .build();

			    HttpComponentsClientHttpRequestFactory requestFactory =
			                    new HttpComponentsClientHttpRequestFactory();
			    requestFactory.setHttpClient(httpClient);
				
			    return requestFactory;
			 
		 };
	    }
	    

}
