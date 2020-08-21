package kafkaReader.kafka.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, Profile.dev.toString());
		String profile = System.getProperty("spring.profiles.active") != null ?
				System.getProperty("spring.profiles.active") :
				System.getProperty("spring.profiles.default");
		printProfile(Profile.valueOf(profile));

		SpringApplication.run(KafkaApplication.class, args);
	}

	enum Profile {
		dev, hml, prd
	}

	private static void printProfile(Profile mode) {
		System.out.println("---------------------------------------------");
		switch (mode) {
			case dev: {
				System.out.println("TCC Kafka Api - DESENVOLVIMENTO");
				break;
			}
			case hml: {
				System.out.println("TCC Kafka Api - HOMOLOGACAO");
				break;
			}
			case prd: {
				System.out.println("TCC Kafka Api - PRODUCAO");
				break;
			}
		}
		System.out.println("---------------------------------------------");
	}

}
