package no.nav.pensjon.brev.bestilling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevErBestilt;

/*
    A trivial test using TestChannelBinder to illustrate how to use this.
    See: https://docs.spring.io/spring-cloud-stream/docs/3.0.8.RELEASE/reference/html/spring-cloud-stream.html#_testing
 */

@SpringBootTest(classes = {TestChannelBinderConfiguration.class})
@RunWith(SpringRunner.class)
@Import({Application.class})
public class ApplicationTestBinderDemo {

    private static final String INPUT_DESTINATION = "pensjonsbrev.brev-er-bestilt";
    private static final String OUTPUT_DESTINATION = "pensjonsbrev.brev-data-grunnlag";

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Test
    public void demoTest() throws IOException {

        BrevErBestilt brevErBestilt = new BrevErBestilt();
        brevErBestilt.setMeldingId("1");

        ObjectMapper objectMapper = new ObjectMapper();
        GenericMessage message = new GenericMessage<>(objectMapper.writeValueAsString(brevErBestilt));
        input.send(message, INPUT_DESTINATION);

        BrevErBestilt result = objectMapper.readValue(output.receive(10_000, OUTPUT_DESTINATION).getPayload(), BrevErBestilt.class);

        assertEquals("1", result.getMeldingId());
    }
}