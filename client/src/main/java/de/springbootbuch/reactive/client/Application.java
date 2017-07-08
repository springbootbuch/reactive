package de.springbootbuch.reactive.client;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@SpringBootApplication
public class Application {

	static final Logger LOG = LoggerFactory
		.getLogger(Application.class);
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		
		final CountDownLatch closeLatch = new CountDownLatch(1);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				closeLatch.countDown();
			}
		});
		closeLatch.await();
	}

	@Bean
	WebClient webClient() {
		return WebClient.create("http://localhost:8080/api");
	}

	@Bean
	CommandLineRunner streamingClient(WebClient client) {
		return args -> 
			client
				.get().uri("/films")
				.retrieve().bodyToFlux(Film.class)
				.filter(film -> 
					film.getTitle().toLowerCase(Locale.GERMAN)
						.contains("goldfinger"))
				.flatMap(film -> 
					client.get()
					.uri("/films/{id}/stream", film.getId())
					.retrieve()
					.bodyToFlux(Film.class))
				.subscribe(film -> 
					LOG.info("Streaming {}...", film.getTitle())
				);
	}
}
