package de.springbootbuch.reactive.filmstore;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
public class DemoRestController {
	@GetMapping("/helloworld")
	public Mono<String> getGreeting(
		@RequestParam(defaultValue = "World") String name
	) {
		return Mono.just("Hello")
			.flatMap(s -> Mono
				.just(s + ", " + name + "!\n")
			);
	}
	
	@PostMapping("/manygreetings")
	public Flux<String> getGreetings(
		@RequestBody Mono<String> name
	) {
		return name
			.flatMapMany(n -> Flux.generate(s -> s.next(n)))
			.zipWith(Flux.range(1, 23))
			.map(t -> "Hello, " + t.getT1() + "\n");
	}
	
	@GetMapping(
		path = "/streamgreetings", 
		produces = MediaType.TEXT_EVENT_STREAM_VALUE
	)
	public Flux<String> streamGreetings() {		
		return Flux.interval(Duration.ofSeconds(1))
			.log()
			.map(i -> String.format("Hello (%d)", i));
	}
}
