package no.nav.pensjon.brev.bestilling;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.nav.joark.JoarkClient;
import no.nav.joark.JoarkConfig;
import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevBestilling;
import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevBestillingRequest;
import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevErBestilt;
import no.nav.pensjon.sts.client.StsClientConfig;

@SpringBootApplication
@RestController
@Import({StsClientConfig.class, JoarkConfig.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private StreamBridge streamBridge;
	@Autowired
	private JoarkClient joarkClient;

	@Bean
	public Function<BrevBestillingRequest, String> brevbestilling(){
		return (request) -> {
			String joarkId = joarkClient.opprett();
			streamBridge.send("brevbestilling-out-0",request);

			return joarkId;
		};
	}

	// Can be removed. Just part of kafka retry poc
	@PostMapping("/sendToKafka")
	public void sendToKafka(@RequestBody String body) {
		System.out.println("Videresender melding fra REST til Kafka. Melding: " + body);
		streamBridge.send("brevbestilling-out-0", string2brevErBestillt(body));
	}

	// Can be removed. Just part of kafka retry poc
	private static BrevErBestilt string2brevErBestillt(String value) {
		BrevErBestilt brevErBestilt = new BrevErBestilt();
		brevErBestilt.setMeldingId(UUID.randomUUID().toString());
		brevErBestilt.setBrevbestillingRequest(new BrevBestilling());
		brevErBestilt.getBrevbestillingRequest().setBrevKode(value);
		return brevErBestilt;
	}
}