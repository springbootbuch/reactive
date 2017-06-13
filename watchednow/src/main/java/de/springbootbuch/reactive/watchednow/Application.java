package de.springbootbuch.reactive.watchednow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	RouterFunction<?> routerFunction(final FilmWatchedEventRepository filmWatchedEventRepository) {
		return route(
			POST("/api/filmWatched"), request -> 
			ok().body(
				request.bodyToMono(Film.class)
					.map(FilmWatchedEvent::new)
					.flatMap(filmWatchedEventRepository::save), 
				FilmWatchedEvent.class
			)
		);
	}
}
