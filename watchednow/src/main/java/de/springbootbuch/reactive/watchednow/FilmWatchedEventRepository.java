package de.springbootbuch.reactive.watchednow;

import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public interface FilmWatchedEventRepository 
	extends ReactiveCrudRepository<FilmWatchedEvent, String> {
	
	@Tailable
	Flux<FilmWatchedEvent> streamAllBy();
}
