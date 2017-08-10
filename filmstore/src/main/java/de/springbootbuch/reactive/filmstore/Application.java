package de.springbootbuch.reactive.filmstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	WebClient webClient(@Value("${watchednow.service.url:http://localhost:8081}") String url) {
		return WebClient
			.create(url)
			.mutate().filter(basicAuthentication("film", "store"))
			.build();
	}
}
