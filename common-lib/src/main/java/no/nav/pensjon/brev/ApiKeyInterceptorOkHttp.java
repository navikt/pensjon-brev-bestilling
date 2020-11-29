package no.nav.pensjon.brev;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptorOkHttp implements Interceptor  {

    private static final String X_NAV_APIKEY_NAME = "x-nav-apiKey";

    private String apiKey;

    public ApiKeyInterceptorOkHttp(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader(X_NAV_APIKEY_NAME, apiKey)
                .build();

        return chain.proceed(request);
    }
}
