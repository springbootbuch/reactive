package de.springbootbuch.reactive.watchednow;

import static java.time.LocalDateTime.now;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
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
	ApiHandler apiHandler(
		FilmWatchedEventRepository repository
	) {
		return new ApiHandler(repository);
	}
	
	@Bean
	SiteHandler siteHandler(
		FilmWatchedEventRepository repository
	) {
		return new SiteHandler(repository);
	}

	@Bean
	RouterFunction<?> routerFunction(
		SiteHandler siteHandler, ApiHandler apiHandler
	) {
		return
			 route(GET("/"), siteHandler::index)
			.andNest(path("/api"),
				 route(POST("/filmWatched"),
					apiHandler::filmWatched)
				.andRoute(
					GET("/watchedRightNow"),
					apiHandler::watchedRightNow));
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

class ApiHandler {

	final FilmWatchedEventRepository repository;

	ApiHandler(FilmWatchedEventRepository repository) {
		this.repository = repository;
	}

	Mono<ServerResponse> filmWatched(
		ServerRequest request
	) {
		return ok().body(
			request.bodyToMono(Film.class)
				.map(FilmWatchedEvent::new)
				.flatMap(repository::save),
			FilmWatchedEvent.class
		);
	}

	Mono<ServerResponse> watchedRightNow(
		ServerRequest request
	) {
		return ok()
			.contentType(TEXT_EVENT_STREAM)
			.body(repository
					.streamAllBy()
					.filter(e -> e.getWatchedOn()
						.isAfter(now().minusSeconds(10))
					)
			,
			FilmWatchedEvent.class
		);
	}
}

class SiteHandler {

	final FilmWatchedEventRepository repository;

	SiteHandler(FilmWatchedEventRepository repository) {
		this.repository = repository;
	}

	Mono<ServerResponse> index(ServerRequest request) {
		Flux<String> filmsBeingWatched =
			repository.streamAllBy()
				.map(f -> f.getTitle())
				.distinct();

		final Map<String, Object> model = new HashMap<>();
		model.put("filmsBeingWatched",
			new ReactiveDataDriverContextVariable(
				filmsBeingWatched, 1));
		return ok().contentType(TEXT_HTML)
			.render("index", model);
	}
}