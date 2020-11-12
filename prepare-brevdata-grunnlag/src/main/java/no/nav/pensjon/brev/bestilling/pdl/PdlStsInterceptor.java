package no.nav.pensjon.brev.bestilling.pdl;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import no.nav.pensjon.sts.client.StsRestClient;

public class PdlStsInterceptor implements ClientHttpRequestInterceptor {

    private final StsRestClient stsRestClient;

    public PdlStsInterceptor(StsRestClient stsRestClient) {
        this.stsRestClient = stsRestClient;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String token = stsRestClient.getToken();
        httpRequest.getHeaders().setBearerAuth(token);
        httpRequest.getHeaders().add("Nav-Consumer-Token", "Bearer " + token);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

}
