package de.springbootbuch.reactive.filmstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Configuration
public class SecurityConfig {

	@Bean
	public ReactiveUserDetailsService userDetailsService() {
		return new MapReactiveUserDetailsService(
			User.withUsername("spring")
				.password("{noop}boot")
				.roles("USER")
				.build()
		);
	}
}
