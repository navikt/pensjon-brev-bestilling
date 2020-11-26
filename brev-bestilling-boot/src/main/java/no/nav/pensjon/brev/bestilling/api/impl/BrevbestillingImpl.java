package no.nav.pensjon.brev.bestilling.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevBestillingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class BrevbestillingImpl {
    private static final Logger logger = LoggerFactory.getLogger(BrevbestillingImpl.class);

    public ResponseEntity<Void> brevbestillingPost(BrevBestillingRequest brevbestillingRequest) {
        try {
            logger.info(new ObjectMapper().writeValueAsString(brevbestillingRequest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
