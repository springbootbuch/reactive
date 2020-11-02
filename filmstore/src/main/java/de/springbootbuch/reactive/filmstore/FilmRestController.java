package de.springbootbuch.reactive.filmstore;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@RestController
public class FilmRestController {
	
	static final Logger LOG = LoggerFactory
		.getLogger(FilmRestController.class);
	
	private final FilmRepository filmRepository;
	
	private final WebClient client;

	public FilmRestController(FilmRepository filmRepository, WebClient webClient) {
		this.filmRepository = filmRepository;
		this.client = webClient;
	}
	
	@GetMapping(
		path = "/api/films",
		produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Film> getAll() {
		return filmRepository
			.findAll(Sort.by("title").ascending());
	}
	
	@GetMapping(
		path = "/api/films/{id}/stream", 
		produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Film> stream(@PathVariable String id) {
		return filmRepository.findById(id)
			.flatMapMany(film -> 
				Flux.<Film>generate(sink -> sink.next(film)))
			.zipWith(Flux.interval(Duration.ofSeconds(1)))
			.doOnNext(this::publishWatchedFilm)
			.map(Tuple2::getT1);
	}
	
	void publishWatchedFilm(Tuple2<Film, Long> t) {
		client
			.post().uri("/api/filmWatched")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.body(Mono.just(t.getT1()), Film.class)
			.retrieve().bodyToFlux(FilmWatchedEvent.class)
			.subscribe(event -> 
				LOG.info("Watched {} for {}s", 
					event.getTitle(), t.getT2())
			);
	}
	
	@GetMapping("/api/films/{id}/actors")
	public Flux<Actor> getActors(@PathVariable String id) {
		return filmRepository.findById(id)
			.flatMapMany(f -> Flux.fromIterable(f.getActors()));
	}
}
