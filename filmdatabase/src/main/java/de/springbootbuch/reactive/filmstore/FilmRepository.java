package de.springbootbuch.reactive.filmstore;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Part of springbootbuch.de.
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public interface FilmRepository 
	extends ReactiveMongoRepository<Film, String> {
}
