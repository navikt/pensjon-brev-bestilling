package no.nav.pensjon.brev.bestilling.pesys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import no.nav.pensjon.brev.ApiKeyInterceptor;
import no.nav.pensjon.sts.client.StsInterceptor;

@Configuration
public class SakConfig {

    @Value("${sak-api.client.apikey}")
    String sakApiKey;

    @Value("${sak-api.client.url}")
    String sakUrl;

    private RestTemplate restTemplate(StsInterceptor stsInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(stsInterceptor);
        restTemplate.getInterceptors().add(new ApiKeyInterceptor(sakApiKey));
        return restTemplate;
    }

    @Bean
    SakClient sakClient(StsInterceptor stsInterceptor) {
        return new SakClient(restTemplate(stsInterceptor), sakUrl);
    }
}
