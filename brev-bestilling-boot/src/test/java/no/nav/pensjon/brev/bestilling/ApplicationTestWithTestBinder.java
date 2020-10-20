package no.nav.pensjon.brev.bestilling;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;

/**
 * Demo of testing with test binder. Allowes testing independently of real binder (kafka, rabbit, ...)
 * https://docs.spring.io/spring-cloud-stream/docs/3.0.8.RELEASE/reference/html/spring-cloud-stream.html#spring_integration_test_binder
 * https://spring.io/blog/2020/07/27/creating-a-supplier-function-and-generating-spring-cloud-stream-source
 */

public class ApplicationTestWithTestBinder {

    @Test
    public void testBestillBrevSupplier() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration
                        .getCompleteConfiguration(Application.class))
                .web(WebApplicationType.NONE)
                .run("--spring.cloud.function.definition=bestillBrevSupplier")) {

            OutputDestination target = context.getBean(OutputDestination.class);
            Message<byte[]> sourceMessage = target.receive(10000);
            String message = new String(sourceMessage.getPayload());
            assertThat(message).isEqualTo("TEST BREV_ER_BESTILLT");
        }
    }
}