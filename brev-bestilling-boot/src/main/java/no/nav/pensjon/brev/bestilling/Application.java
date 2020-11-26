package no.nav.pensjon.brev.bestilling;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import no.nav.pensjon.brev.bestilling.integrasjon.model.BrevBestillingRequest;
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

	@Autowired
	private StreamBridge streamBridge;

	@Bean
	public Function<BrevBestillingRequest, String> brevbestilling(){
		return (param1) -> {
			streamBridge.send("brevbestilling-out-0",param1);

			return "antilope";
		};
	}

	@Bean
	public Function<String, String> brevErBestillt(){
		return (param1) -> {
			return param1;
		};
	}
}