package no.nav.pensjon.brev.bestilling.security;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class STSInterceptor implements ClientHttpRequestInterceptor {
    
    private final StsRestClient stsRestClient;

    public STSInterceptor(StsRestClient stsRestClient) {
        this.stsRestClient = stsRestClient;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().setBearerAuth(stsRestClient.getToken());
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
