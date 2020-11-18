package no.nav.pensjon.brev.bestilling.pesys;

import no.nav.pensjon.brev.ApiKeyInterceptorSpring;
import no.nav.pensjon.sts.client.StsInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VedtakConfig {

    @Value("${vedtak-api.client.apikey}")
    String vedtakApiKey;

    @Value("${vedtak-api.client.url}")
    String vedtakUrl;

    private RestTemplate restTemplate(StsInterceptor stsInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(stsInterceptor);
        restTemplate.getInterceptors().add(new ApiKeyInterceptorSpring(vedtakApiKey));
        return restTemplate;
    }

    @Bean
    VedtakClient vedtakClient(StsInterceptor stsInterceptor) {
        return new VedtakClient(restTemplate(stsInterceptor), vedtakUrl);
    }
}
