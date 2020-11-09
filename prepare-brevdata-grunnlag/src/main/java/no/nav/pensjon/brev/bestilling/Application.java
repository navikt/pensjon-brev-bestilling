package no.nav.pensjon.brev.bestilling;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import no.nav.pensjon.brev.bestilling.pdl.PdlClient;
import no.nav.pensjon.brev.bestilling.pdl.PdlClientConfig;
import no.nav.pensjon.sts.client.StsClientConfig;
import no.nav.pensjon.sts.client.StsRestClient;

@SpringBootApplication
@Import({StsClientConfig.class, PdlClientConfig.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * The background function which triggers on an event from Pub/Sub and consumes the Pub/Sub
	 * event message.
	 */
	@Bean
	public Consumer<PubSubMessage> pubSubFunction() {
		return message -> {
			// The PubSubMessage data field arrives as a base-64 encoded string and must be decoded.
			// See: https://cloud.google.com/functions/docs/calling/pubsub#event_structure
			String decodedMessage = new String(Base64.getDecoder().decode(message.getData()), StandardCharsets.UTF_8);
			System.out.println("Received Pub/Sub message with data: " + decodedMessage + message.getPublishTime() + " " + message.getMessageId());
		};
	}

	@Bean
	public Function<String, String> brevErBestiltFunction() {
		return value -> {
			System.out.println("Received: " + value);
			return value.toLowerCase();
		};
	}

	@Autowired
	PdlClient pdlClient;

	@PostConstruct
	void testPdl() {
		pdlClient.query();
	}
}