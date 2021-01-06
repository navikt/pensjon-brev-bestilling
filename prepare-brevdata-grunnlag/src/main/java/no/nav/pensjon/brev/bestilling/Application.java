package no.nav.pensjon.brev.bestilling;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.endpoint.BindingsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevErBestilt;
import no.nav.pensjon.brev.bestilling.pdl.PdlClientConfig;
import no.nav.pensjon.brev.bestilling.processor.BrevErBestilltProcessor;
import no.nav.pensjon.brev.bestilling.processor.ProcessorConfig;
import no.nav.pensjon.sts.client.StsClientConfig;

@SpringBootApplication
@Import({StsClientConfig.class, PdlClientConfig.class, ProcessorConfig.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private BindingsEndpoint bindingsEndpoint;

	@Autowired
	private BrevErBestilltProcessor brevErBestilltProcessor;

	private Set<String> attemptedRetryIds = new HashSet<>();

	@Bean
	public Function<BrevErBestilt, BrevErBestilt> brevErBestilt() {
		return value -> brevErBestilltProcessor.process(value);
	}

	// Stops processing of DLT when the same message has been attempted twice
	@Bean
	public Function<BrevErBestilt, BrevErBestilt> brevErBestiltDlt() {
		return brevErBestilt -> {
			String messageId = brevErBestilt.getMeldingId();
			if (attemptedRetryIds.contains(messageId)){
				bindingsEndpoint.changeState("brevErBestiltDlt-in-0", BindingsEndpoint.State.STOPPED);
				throw new IllegalStateException("brevErBestiltDlt-in-0 is shutting down.");
			}

			try {
				return brevErBestilltProcessor.process(brevErBestilt);
			}
			catch (Exception e) {
				attemptedRetryIds.add(messageId);
				throw e;
			}
		};
	}

}