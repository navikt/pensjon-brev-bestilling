package no.nav.joark;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import no.nav.pensjon.brev.ApiKeyInterceptor;
import no.nav.pensjon.sts.client.StsInterceptor;

@Configuration
public class JoarkConfig {
    @Value("${joark-api.client.apikey}")
    String joarkApiKey;

    @Value("${joark-api.client.url}")
    String joarkUrl;

    private RestTemplate restTemplate(StsInterceptor stsInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        JsonMapper jsonMapper = JsonMapper.builder()
                .addModule(new KotlinModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build();
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(jsonMapper);
        restTemplate.getMessageConverters().add(messageConverter);
        restTemplate.getInterceptors().add(stsInterceptor);
        restTemplate.getInterceptors().add(new ApiKeyInterceptor(joarkApiKey));
        return restTemplate;
    }

    @Bean
    JoarkClient joarkClient(StsInterceptor stsInterceptor) {
        return new JoarkClient(restTemplate(stsInterceptor), joarkUrl);
    }
}
