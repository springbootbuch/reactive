package de.springbootbuch.reactive.filmstore;

import de.springbootbuch.reactive.filmstore.support.ActorConstructorMixIn;
import de.springbootbuch.reactive.filmstore.support.FilmConstructorMixIn;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Part of springbootbuch.de.
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	Jackson2ObjectMapperBuilderCustomizer objectMapperCustomizer() {
		return builder -> {
			builder.mixIn(Film.class, FilmConstructorMixIn.class);
			builder.mixIn(Actor.class, ActorConstructorMixIn.class);
		};
	}
}
