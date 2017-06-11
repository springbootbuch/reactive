package de.springbootbuch.reactive.filmstore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Part of springbootbuch.de.
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
		return filmRepository.findAll();
	}
}