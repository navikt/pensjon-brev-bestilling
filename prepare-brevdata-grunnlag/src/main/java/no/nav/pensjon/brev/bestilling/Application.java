package no.nav.pensjon.brev.bestilling;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.endpoint.BindingsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevErBestilt;
import no.nav.pensjon.brev.bestilling.pdl.PdlClientConfig;
import no.nav.pensjon.sts.client.StsClientConfig;

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



	@Autowired
	private BindingsEndpoint bindingsEndpoint;

	private Set<String> attemptedRetryIds = new HashSet<>();

	@Bean
	public Function<BrevErBestilt, BrevErBestilt> brevErBestilt() {
		return value -> processBrevErBestilt(value);
	}

	// Stops processing of DLT when the same message has been attempted twice
	@Bean
	public Function<BrevErBestilt, BrevErBestilt> brevErBestiltDlt() {
		return brevErBestilt -> {
			String messageId = brevErBestilt.getMeldingId();
			if (attemptedRetryIds.contains(messageId)){
				bindingsEndpoint.changeState("brevErBestiltDlt-in-0", BindingsEndpoint.State.STOPPED);
			}

			try {
				return processBrevErBestilt(brevErBestilt);
			}
			catch (Exception e) {
				attemptedRetryIds.add(messageId);
				throw e;
			}
		};
	}

	/*
		Dummy function to simulate errors happening. Can be removed.
		Just here for illustration purposes.
	 */
	private int sometimesCount = 0;
	private BrevErBestilt processBrevErBestilt(BrevErBestilt brevErBestilt) {

//		if (brevErBestilt.getBrevbestillingRequest() == null ||
//				brevErBestilt.getBrevbestillingRequest().getBrevKode() == null) {
//			return brevErBestilt;
//		}
//
//		if (brevErBestilt.getBrevbestillingRequest().getBrevKode().contains("sometimes")) {
//			sometimesCount++;
//			if (sometimesCount % 4 != 0) {
//				throw new RuntimeException("sometimes failed.");
//			}
//		}
//		else if (brevErBestilt.getBrevbestillingRequest().getBrevKode().contains("error")) {
//			throw new RuntimeException("error failed.");
//		}

		return brevErBestilt;
	}
}