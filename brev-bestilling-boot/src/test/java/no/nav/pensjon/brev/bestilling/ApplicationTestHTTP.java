package no.nav.pensjon.brev.bestilling;

import no.nav.pensjon.brev.bestilling.integrasjon.model.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import scala.annotation.varargs;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ApplicationTestHTTP {

    private static final String OUTPUT_TOPIC = "pensjonsbrev.brev-er-bestilt";
    private static final String GROUP_NAME = "embeddedKafkaApplication";

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, OUTPUT_TOPIC);

    @BeforeClass
    public static void setup() {
        System.setProperty("spring.cloud.stream.kafka.binder.brokers", embeddedKafka.getEmbeddedKafka().getBrokersAsString());
    }

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void test() throws Exception {
        BrevbestillingRequest request = new BrevbestillingRequest()
                .brevKode("0000129")

                .sprak(new Sprak().sprakKode("NB"))
                .sensitivt(true)
                .kravId("Krav1")
                .vedtakId("VedatkId1")
                .adresseringsInformasjon(new AdresseringsInformasjon()
                        .mottaker("Mottaker 1")
                        .land(new Land().landKode("NO")))
                .sak(new Sak().sakId("000001")
                        .gjelder("saken gjelder")
                        .journalfoerendeEnhet("journalførendeEnhetNR1")
                        .saksbehandler(new Saksbehandler()
                                .saksbehandlerId("00002")
                                .saksbehandlerNavn("Sak Behandlersen")));

        ResponseEntity<String> result = this.rest.exchange(
                RequestEntity.post(new URI("/brevbestilling")).body(request), String.class);
        System.out.println("giraff" + result.getBody());
        Assert.assertEquals(200, result.getStatusCodeValue());

        assertKafka();
    }

    private void assertKafka(){

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(GROUP_NAME, "false", embeddedKafka.getEmbeddedKafka());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put("key.deserializer", ByteArrayDeserializer.class);
        consumerProps.put("value.deserializer", ByteArrayDeserializer.class);
        DefaultKafkaConsumerFactory<byte[], byte[]> cf = new DefaultKafkaConsumerFactory<>(consumerProps);

        Consumer<byte[], byte[]> consumer = cf.createConsumer();
        consumer.subscribe(Collections.singleton(OUTPUT_TOPIC));
        ConsumerRecords<byte[], byte[]> records = consumer.poll(10_000);
        consumer.commitSync();

        assertThat(records.count()).isEqualTo(1);
        assertThat(new String(records.iterator().next().value())).isEqualTo("{\"brevKode\":\"0000129\",\"sprak\":{\"sprakKode\":\"NB\"},\"sensitivt\":true,\"kravId\":\"Krav1\",\"vedtakId\":\"VedatkId1\",\"adresseringsInformasjon\":{\"mottaker\":\"Mottaker 1\",\"land\":{\"landKode\":\"NO\"}},\"sak\":{\"sakId\":\"000001\",\"gjelder\":\"saken gjelder\",\"journalfoerendeEnhet\":\"journalførendeEnhetNR1\",\"saksbehandler\":{\"saksbehandlerNavn\":\"Sak Behandlersen\",\"saksbehandlerId\":\"00002\"}}}");
    }
}
