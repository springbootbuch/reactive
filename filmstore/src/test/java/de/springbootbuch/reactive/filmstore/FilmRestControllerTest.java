package de.springbootbuch.reactive.filmstore;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.*;

import reactor.test.StepVerifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureWebTestClient
public class FilmRestControllerTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void filmApiShouldWork() {
		FluxExchangeResult<Film> result = client
			.mutate()
				.filter(basicAuthentication("spring","boot"))
			.build()
			.get().uri("/api/films")
			.accept(TEXT_EVENT_STREAM)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(TEXT_EVENT_STREAM_VALUE)
			.returnResult(Film.class);

		StepVerifier.create(result.getResponseBody())
			.consumeNextWith(film ->
				assertThat(film.getTitle())
					.isEqualTo("ACADEMY DINOSAUR")
			)
			.expectNextCount(9)
			.expectComplete()
			.verify();
	}
}
