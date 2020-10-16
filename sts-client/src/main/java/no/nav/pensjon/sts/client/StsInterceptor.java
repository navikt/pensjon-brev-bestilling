package no.nav.pensjon.sts.client;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class StsInterceptor implements ClientHttpRequestInterceptor {
    
    private final StsRestClient stsRestClient;

    public StsInterceptor(StsRestClient stsRestClient) {
        this.stsRestClient = stsRestClient;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().setBearerAuth(stsRestClient.getToken());
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
