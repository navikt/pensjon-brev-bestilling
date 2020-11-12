package no.nav.pensjon.brev;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class ApiKeyInterceptor implements ClientHttpRequestInterceptor {

    private static final String X_NAV_APIKEY_NAME = "x-nav-apiKey";

    private String apiKey;

    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().add(X_NAV_APIKEY_NAME, apiKey);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

}
