package no.nav.joark;

import no.nav.pensjon.brev.ApiKeyInterceptor;
import no.nav.pensjon.sts.client.StsInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JoarkConfig {
    @Value("${joark-api.client.apikey")
    String joarkApiKey;

    @Value("${joark-api.client.url}")
    String joarkUrl;

    private RestTemplate restTemplate(StsInterceptor stsInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(stsInterceptor);
        restTemplate.getInterceptors().add(new ApiKeyInterceptor(joarkApiKey));
        return restTemplate;
    }

    @Bean
    JoarkClient joarkClient(StsInterceptor stsInterceptor) {
        return new JoarkClient(restTemplate(stsInterceptor), joarkUrl);
    }
}
