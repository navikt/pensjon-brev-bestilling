package no.nav.pensjon.brev.bestilling.pdl;

import com.apollographql.apollo.ApolloClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

import no.nav.pensjon.brev.ApiKeyInterceptorOkHttp;
import no.nav.pensjon.sts.client.StsRestClient;

@Configuration
public class PdlClientConfig {

    @Value("${pdl-api.client.apikey}")
    String pdlApiKey;

    @Value("${pdl-api.client.url}")
    String pdlUrl;

    private ApolloClient apolloClient(StsRestClient stsRestClient) {
        return ApolloClient.builder()
                .serverUrl(pdlUrl)
                .okHttpClient((new OkHttpClient.Builder())
                        .addInterceptor(new PdlStsInterceptor(stsRestClient))
                        .addInterceptor(new ApiKeyInterceptorOkHttp(pdlApiKey))
                        .build())
                .build();
    }

    @Bean
    PdlClient pdlClient(StsRestClient stsRestClient) {
        return new PdlClient(apolloClient(stsRestClient));
    }

}
