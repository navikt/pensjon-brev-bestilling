package no.nav.pensjon.brev.bestilling.pdl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import no.nav.pensjon.brev.ApiKeyInterceptor;
import no.nav.pensjon.sts.client.StsRestClient;

@Configuration
public class PdlClientConfig {

    @Value("${pdl-api.client.apikey}")
    String pdlApiKey;

    @Value("${pdl-api.client.url}")
    String pdlUrl;

    @Bean
    RestTemplate restTemplate(StsRestClient stsRestClient) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new PdlStsInterceptor(stsRestClient));
        restTemplate.getInterceptors().add(new ApiKeyInterceptor(pdlApiKey));
        return restTemplate;
    }

    @Bean
    PdlClient pdlClient(RestTemplate restTemplate) {
        return new PdlClient(restTemplate, pdlUrl);
    }

}
