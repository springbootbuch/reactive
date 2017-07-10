package de.springbootbuch.reactive.filmstore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureWebTestClient
public class FilmRestControllerTest {
	
	@Autowired
	private WebTestClient client;
	
	@Test
	public void filmApiShouldWork() {
		FluxExchangeResult<Film> result = client.get()
			.uri("/api/films")
			.accept(TEXT_EVENT_STREAM)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(TEXT_EVENT_STREAM)
			.returnResult(Film.class);
		
		StepVerifier.create(result.getResponseBody())				
			.consumeNextWith(film -> 
				assertThat(film.getTitle(), 
					is("ACADEMY DINOSAUR"))
			)
			.expectNextCount(9)
			.expectComplete()
			.verify();
	}
}
