package de.springbootbuch.reactive.watchednow;

import java.time.LocalDateTime;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import reactor.core.publisher.Mono;

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
	RouterFunction<?> routerFunction(final Handler handler) {
		return route(POST("/api/filmWatched"), handler::filmWatched)
			.andRoute(GET("/api/watchedRightNow"), handler::watchedRightNow);
	}
	
	@Bean
	ApplicationRunner applicationRunner(MongoOperations operations) {
		return args -> {
			if (operations.collectionExists(FilmWatchedEvent.class)) {
				operations.dropCollection(FilmWatchedEvent.class);
			}
			operations.createCollection(
				FilmWatchedEvent.class,
				CollectionOptions.empty()
					.maxDocuments(100L)
					.size(1_048_576L)
					.capped());
		};
	}
}

@Component
class Handler {

	private final FilmWatchedEventRepository filmWatchedEventRepository;

	Handler(FilmWatchedEventRepository filmWatchedEventRepository) {
		this.filmWatchedEventRepository = filmWatchedEventRepository;
	}

	Mono<ServerResponse> filmWatched(ServerRequest request) {
		return ok().body(
			request.bodyToMono(Film.class)
				.map(FilmWatchedEvent::new)
				.flatMap(filmWatchedEventRepository::save),
			FilmWatchedEvent.class
		);
	}
	
	Mono<ServerResponse> watchedRightNow(ServerRequest request) {
		return ok()
			.contentType(MediaType.TEXT_EVENT_STREAM)
			.body(
				filmWatchedEventRepository
					.streamAllBy()
					.filter(e -> e.getWatchedOn()
						.isAfter(LocalDateTime.now().minusSeconds(10))
					)
			, 
			FilmWatchedEvent.class
		);
	}
}
