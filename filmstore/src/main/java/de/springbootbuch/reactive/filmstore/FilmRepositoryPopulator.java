package de.springbootbuch.reactive.filmstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Component
public class FilmRepositoryPopulator implements CommandLineRunner {

	static final Logger LOG = LoggerFactory
		.getLogger(FilmRepositoryPopulator.class);

	private final ResourceLoader resourceLoader;

	private final ObjectMapper objectMapper;

	private final FilmRepository filmRepository;

	public FilmRepositoryPopulator(ResourceLoader resourceLoader, ObjectMapper objectMapper, FilmRepository filmRepository) {
		this.resourceLoader = resourceLoader;
		this.objectMapper = objectMapper;
		this.filmRepository = filmRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		final Film[] films = loadFilms();

		this.filmRepository
			.deleteAll()
			.thenMany(Flux.just(films))
			.flatMap(f -> this.filmRepository.save(f))
			.doOnNext(f -> 
				LOG.info("Film '{}' (id={}) saved", 
					f.getTitle(), f.getId()))
			.blockLast();
	}

	private Film[] loadFilms() throws IOException {
		// This is a blocking operation...
		// Didn't find a simple way to present it in the book.
		// Feel free to send me a PR :)
		return this.objectMapper.readValue(
				this.resourceLoader.getResource("classpath:/data.json").getInputStream(),
				Film[].class);
	}
}
