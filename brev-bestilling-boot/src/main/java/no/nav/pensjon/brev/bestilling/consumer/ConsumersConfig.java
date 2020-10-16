package no.nav.pensjon.brev.bestilling.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConsumersConfig {

    @Bean
    public BrevMetaData brevMetaData() {
        return new BrevMetaData();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
