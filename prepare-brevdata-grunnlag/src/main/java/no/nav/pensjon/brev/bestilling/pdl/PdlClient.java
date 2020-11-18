package no.nav.pensjon.brev.bestilling.pdl;

import static no.nav.pensjon.brev.bestilling.pdl.ApolloClientUtils.toCompletableFuture;

import java.util.Optional;

import com.apollographql.apollo.ApolloClient;

import no.nav.pensjon.brev.bestilling.pdl.graphql.queries.HentIdenterQuery;

public class PdlClient {

    private ApolloClient apolloClient;

    private static String testIdent = "12345678910";

    public PdlClient(ApolloClient apolloClient) {
        this.apolloClient = apolloClient;
    }

    public void hentIdenter() {
        Optional<HentIdenterQuery.Data> response = toCompletableFuture(
                apolloClient.query(new HentIdenterQuery(testIdent)))
                .join()
                .getData();

        System.out.println("The response=================\n"+response.toString());
    }
}
