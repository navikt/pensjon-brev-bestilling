package no.nav.pensjon.brev.bestilling.pdl;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import no.nav.pensjon.sts.client.StsRestClient;

public class PdlStsInterceptor implements Interceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String NAV_CONSUMER_TOKEN = "Nav-Consumer-Token";

    private final StsRestClient stsRestClient;

    public PdlStsInterceptor(StsRestClient stsRestClient) {
        this.stsRestClient = stsRestClient;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String bearerToken = "Bearer " + stsRestClient.getToken();
        Request request = chain.request().newBuilder()
                .addHeader(AUTHORIZATION, bearerToken)
                .addHeader(NAV_CONSUMER_TOKEN, bearerToken)
                .addHeader("Tema", "PEN")
                .build();

        return chain.proceed(request);
    }
}
