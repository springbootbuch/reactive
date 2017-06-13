package de.springbootbuch.reactive.watchednow.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.springbootbuch.reactive.watchednow.Film;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Configuration
public class JacksonConfig {

	@Bean
	Jackson2ObjectMapperBuilderCustomizer objectMapperBuilderCustomizer() {
		return ombc -> {
			ombc.mixIn(Film.class, FilmConstructorMixin.class);
		};
	}

	@Bean
	Jackson2JsonEncoder jackson2JsonEncoder(final ObjectMapper objectMapper) {
		return new Jackson2JsonEncoder(objectMapper);
	}
	
	@Bean
	Jackson2JsonDecoder jackson2JsonDecoder(final ObjectMapper objectMapper) {
		return new Jackson2JsonDecoder(objectMapper);
	}

	@Bean
	WebFluxConfigurer webFluxConfigurer(
		final Jackson2JsonEncoder encoder, 
		final Jackson2JsonDecoder decoder
	) {
		return new WebFluxConfigurer() {
			@Override
			public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
				configurer.defaultCodecs().jackson2Encoder(encoder);
				configurer.defaultCodecs().jackson2Decoder(decoder);
			}
		};
	}
}
