package no.nav.pensjon.brev.bestilling;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * The background function which triggers on an event from Pub/Sub and consumes the Pub/Sub
	 * event message.
	 */
	@Bean
	public Consumer<String> pubSubFunction() {
		return message -> {
			// The PubSubMessage data field arrives as a base-64 encoded string and must be decoded.
			// See: https://cloud.google.com/functions/docs/calling/pubsub#event_structure
			//String decodedMessage = new String(Base64.getDecoder().decode(message.getData()), StandardCharsets.UTF_8);
			System.out.println("Received Pub/Sub message with data: " + message);
		};
	}

	/**
	 * Just dummy Function and Supplier
	 */
	@Bean
	public Function<byte[], byte[]> bestillBrevFunction(){
		return in -> new String(in).toUpperCase().getBytes();
	}

	@Bean
	public Supplier<byte[]> bestillBrevSupplier(){
		return () -> "TEST BREV_ER_BESTILLT".getBytes();
	}
}