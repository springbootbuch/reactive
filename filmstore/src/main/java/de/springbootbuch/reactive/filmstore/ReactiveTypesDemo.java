package de.springbootbuch.reactive.filmstore;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Component
public class ReactiveTypesDemo  implements CommandLineRunner {

	static final Logger LOG = LoggerFactory
		.getLogger(ReactiveTypesDemo.class);

	@Override
	public void run(String... strings) throws Exception {
		Mono.just("Hallo, Welt!")
			.subscribe(LOG::info);
		
		Mono.just("Hallo")
			.flatMap(s -> Mono.just(s + ", Welt!"))
			.subscribe(LOG::info);
		
		Mono.just("Hallo").concatWith(Flux.just(",", "Welt"))
			.subscribe(LOG::info);

		Flux.just("Guten Tag", "Bonjour", "Ciao")
			.subscribe(LOG::info);
		
		Flux.interval(Duration.ofMillis(1L))
			.subscribeOn(Schedulers.parallel())
			.take(5)
			.map(l -> Long.toString(l))
			.subscribe(LOG::info);
	}
	
}
