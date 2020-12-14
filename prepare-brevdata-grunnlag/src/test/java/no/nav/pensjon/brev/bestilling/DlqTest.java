package no.nav.pensjon.brev.bestilling;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.cloud.stream.endpoint.BindingsEndpoint;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevErBestilt;
import no.nav.pensjon.brev.bestilling.processor.BrevErBestilltProcessor;


@SpringBootTest(classes = {TestChannelBinderConfiguration.class})
@RunWith(SpringRunner.class)
@Import({Application.class})
public class DlqTest {

    private static final String DLT_DESTINATION = "pensjonsbrev.brev-er-bestilt-dlt";

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @MockBean
    BindingsEndpoint bindingsEndpointMock;

    @MockBean
    BrevErBestilltProcessor brevErBestilltProcessorMock;

    @Test
    public void dltProcessorShouldShutDownAfterAttemptingTheSameMessageTwice() throws IOException, InterruptedException {
        when(brevErBestilltProcessorMock.process(any())).thenThrow(new NullPointerException());

        BrevErBestilt brevErBestilt = new BrevErBestilt();
        brevErBestilt.setMeldingId("1");

        ObjectMapper objectMapper = new ObjectMapper();
        GenericMessage message1 = new GenericMessage<>(objectMapper.writeValueAsString(brevErBestilt));
        input.send(message1, DLT_DESTINATION);
        input.send(message1, DLT_DESTINATION);

        verify(brevErBestilltProcessorMock, times(1)).process(any());
        verify(bindingsEndpointMock, atLeastOnce()).changeState("brevErBestiltDlt-in-0", BindingsEndpoint.State.STOPPED);
    }
}