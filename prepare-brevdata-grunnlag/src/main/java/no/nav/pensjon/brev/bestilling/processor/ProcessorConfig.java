package no.nav.pensjon.brev.bestilling.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfig {

    @Bean
    public BrevErBestilltProcessor brevErBestilltProcessor() {
        return new BrevErBestilltProcessor();
    }
}
