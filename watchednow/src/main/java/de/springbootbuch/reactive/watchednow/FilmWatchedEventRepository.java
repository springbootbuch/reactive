package de.springbootbuch.reactive.watchednow;

import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public interface FilmWatchedEventRepository  extends 
	Repository<FilmWatchedEvent, String> {
	
	Mono<FilmWatchedEvent> save(FilmWatchedEvent event);
	
	@Tailable
	Flux<FilmWatchedEvent> streamAllBy();
}
