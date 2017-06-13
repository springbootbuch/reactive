package de.springbootbuch.reactive.watchednow;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@RestController
public class WatchedNowController {
	
	private final FilmWatchedEventRepository filmWatchedEventRepository;

	public WatchedNowController(FilmWatchedEventRepository filmWatchedEventRepository) {
		this.filmWatchedEventRepository = filmWatchedEventRepository;
	}

	@PostMapping("/api/filmWatched")
	public Mono<FilmWatchedEvent> filmWatched(@RequestBody final Mono<Film> film) {
		return film
			.map(FilmWatchedEvent::new)
			.flatMap(filmWatchedEventRepository::save);
	}
}
