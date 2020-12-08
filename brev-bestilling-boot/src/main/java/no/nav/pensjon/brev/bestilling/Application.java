package no.nav.pensjon.brev.bestilling;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import no.nav.joark.JoarkClient;
import no.nav.joark.JoarkConfig;
import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevBestillingRequest;
import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevErBestilt;
import no.nav.pensjon.brev.bestilling.integrasjon.model.JournalFoering;
import no.nav.pensjon.sts.client.StsClientConfig;

@SpringBootApplication
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
			String journalPostId = joarkClient.opprett();
			BrevErBestilt brevErBestilt = new BrevErBestilt();
			brevErBestilt.setJorunalforing(new JournalFoering().jorunalPostId(journalPostId));

			streamBridge.send("brevbestilling-out-0",request);

			return journalPostId;
		};
	}

}