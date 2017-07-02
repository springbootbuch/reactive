package de.springbootbuch.reactive.filmstore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@RestController
public class DemoRestController {
	@GetMapping("/helloworld")
	public Mono<String> getGreeting(
		@RequestParam(defaultValue = "Welt") String name
	) {
		return Mono.just("Hallo")
			.flatMap(s -> Mono
				.just(s + ", " + name + "!\n")
			);
	}
}
