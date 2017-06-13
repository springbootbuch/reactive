package de.springbootbuch.reactive.watchednow;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public interface FilmWatchedEventRepository 
	extends ReactiveMongoRepository<FilmWatchedEvent, String> {
}
