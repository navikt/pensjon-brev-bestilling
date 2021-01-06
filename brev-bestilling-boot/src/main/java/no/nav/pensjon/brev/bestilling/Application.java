package no.nav.pensjon.brev.bestilling;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import no.nav.joark.JoarkClient;
import no.nav.joark.JoarkConfig;
import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevBestillingRequest;
import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevErBestilt;
import no.nav.pensjon.brev.bestilling.integrasjon.model.JournalFoering;
import no.nav.pensjon.sts.client.StsClientConfig;

@SpringBootApplication
@RestController("/")
@Import({StsClientConfig.class, JoarkConfig.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private StreamBridge streamBridge;
	@Autowired
	private JoarkClient joarkClient;

	@PostMapping("brevbestilling")
	public String brevbestilling(BrevBestillingRequest request){
		String journalPostId = joarkClient.opprett();
		BrevErBestilt brevErBestilt = new BrevErBestilt();
		brevErBestilt.setJorunalforing(new JournalFoering().jorunalPostId(journalPostId));

		brevErBestilt.setMeldingId(UUID.randomUUID().toString()); // should be some sort of correlation id. Also important for DLQ/retry behaviour

		streamBridge.send("brevbestilling-out-0", brevErBestilt);

		return journalPostId;
	}

}