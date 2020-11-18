package no.nav.pensjon.brev.bestilling.pdl;

import java.util.concurrent.CompletableFuture;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

public class ApolloClientUtils {

    public static <T> CompletableFuture<Response<T>> toCompletableFuture(ApolloCall<T> apolloCall) {
        CompletableFuture<Response<T>> completableFuture = new CompletableFuture<>();

        completableFuture.whenComplete((tResponse, throwable) -> {
            if (completableFuture.isCancelled()) {
                completableFuture.cancel(true);
            }
        });

        apolloCall.enqueue(new ApolloCall.Callback<T>() {
            @Override
            public void onResponse(Response<T> response) {
                completableFuture.complete(response);
            }

            @Override
            public void onFailure(ApolloException e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture;
    }
}
