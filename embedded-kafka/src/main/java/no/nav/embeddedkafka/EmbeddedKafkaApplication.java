package no.nav.embeddedkafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@SpringBootApplication
public class EmbeddedKafkaApplication {

	private static final String BREV_ER_BESTILT = "pensjonsbrev.brev-er-bestilt";
	private static final String BREV_ER_BESTILT_DLT = BREV_ER_BESTILT + "-dlt";
	private static final String BREV_DATA_GRUNNLAG = "pensjonsbrev.brev-data-grunnlag";

	public static void main(String[] args) {
		SpringApplication.run(EmbeddedKafkaApplication.class, args);
	}

	@Bean
	EmbeddedKafkaBroker broker() {
		return new EmbeddedKafkaBroker(1)
				.kafkaPorts(9092)
				.brokerListProperty("spring.kafka.bootstrap-servers"); // override application property
	}


	@Bean
	public NewTopic brevErBestiltTopic() {
		return TopicBuilder.name(BREV_ER_BESTILT).partitions(1).replicas(1).build();
	}

	@KafkaListener(id = "brevErBestilltListener", topics = BREV_ER_BESTILT)
	public void brevErBestiltListener(String in) {
		printTopicMessage(BREV_ER_BESTILT, in);
	}

	@Bean
	public NewTopic brevErBestiltDltTopic() {
		return TopicBuilder.name(BREV_ER_BESTILT_DLT).partitions(1).replicas(1).build();
	}

	@KafkaListener(id = "brevErBestilltDltListener", topics = BREV_ER_BESTILT_DLT)
	public void brevErBestiltDltListener(String in) {
		printTopicMessage(BREV_ER_BESTILT_DLT, in);
	}


	@Bean
	public NewTopic brevDataGrunnlagTopic() {
		return TopicBuilder.name(BREV_DATA_GRUNNLAG).partitions(1).replicas(1).build();
	}

	@KafkaListener(id = "brevDataGrunnlagListener", topics = BREV_DATA_GRUNNLAG)
	public void brevDataGrunnlagListener(String in) {
		printTopicMessage(BREV_DATA_GRUNNLAG, in);
	}


	/**
	 *	Can be used if you want to post a message directly to a topic at startup.
	 *
	@Bean
	public ApplicationRunner runner(KafkaTemplate<String, String> template) {
		return args -> template.send(BREV_ER_BESTILT, "foo");
	}
	*/

	private static void printTopicMessage(String topic, String msg) {
		System.out.println("Message on topic '" + topic + "':\n" + msg + "\n");
	}
}
