package de.springbootbuch.reactive.filmstore;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@RestController
public class FilmRestController {
	
	private final FilmRepository filmRepository;

	public FilmRestController(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}
	
	@GetMapping("/api/films")
	public Flux<Film> getAll() {
		return filmRepository.findAll(Sort.by("title").ascending());
	}
	
	@GetMapping("/api/films/{id}")
	public Mono<Film> getFilm(@PathVariable final String id) {
		return Mono.justOrEmpty(id)
			.flatMap(filmRepository::findById);
	}
	
	@GetMapping("/api/films/{id}/actors")
	public Flux<Actor> getActors(@PathVariable final String id) {
		return Mono.justOrEmpty(id)
			.flatMap(filmRepository::findById)
			.flatMapMany(f -> Flux.fromIterable(f.getActors()));
	}
}