package no.nav.pensjon.brev.bestilling;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevbestillingRequest;
import no.nav.pensjon.sts.client.StsClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Import(StsClientConfig.class)
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

//	@Bean
//	public Supplier<byte[]> bestillBrevSupplier(){
//		return () -> "TEST BREV_ER_BESTILLT".getBytes();
//	}

	@Autowired
	public StreamBridge streamBridge;

	@Bean
	public Function<BrevbestillingRequest, String> brevbestilling(){
		return (param1) -> {
			streamBridge.send("brevbestilling-out-0",param1);
			brevErBestillt();

			System.out.println(param1);
			return "antilope";
		};
	}

	@Bean
	public Function<String, String> brevErBestillt(){
		return (param1) -> {
			System.out.println("Da er vi her" + param1 + "erBestillt");
			return param1;
		};
	}
}