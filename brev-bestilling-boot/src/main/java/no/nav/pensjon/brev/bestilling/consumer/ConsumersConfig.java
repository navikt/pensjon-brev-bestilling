package no.nav.pensjon.brev.bestilling.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConsumersConfig {

    @Bean
    public BrevMetaData brevMetaData(@Value("${brevmetadata.client.url}") String url, RestTemplate restTemplate) {
        return new BrevMetaData(url, restTemplate);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
