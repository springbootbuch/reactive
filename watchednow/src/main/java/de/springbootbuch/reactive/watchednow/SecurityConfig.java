package de.springbootbuch.reactive.watchednow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

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
			User.withUsername("visitor")
				.password("{noop}visitor")
				.roles("USER")
				.build(),
			User.withUsername("film")
				.password("{noop}store")
				.roles("STORE")
				.build()
		);
	}

	@Bean
	SecurityWebFilterChain springSecurity(
		ServerHttpSecurity http
	) {
		return http
			.authorizeExchange()
			.pathMatchers("/api/watchedRightNow")
				.authenticated()
			.pathMatchers("/api/filmWatched")
				.hasRole("STORE")
			.anyExchange().permitAll()
			.and()
			.build();
	}
}
