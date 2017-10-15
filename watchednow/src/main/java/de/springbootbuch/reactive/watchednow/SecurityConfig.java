package de.springbootbuch.reactive.watchednow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.HttpSecurity;

import org.springframework.security.core.userdetails.MapUserDetailsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsRepository;
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
	public UserDetailsRepository userDetailsRepository() {
		return new MapUserDetailsRepository(
			User.withUsername("visitor")
				.password("visitor")
				.roles("USER")
				.build(),
			User.withUsername("film")
				.password("store")
				.roles("STORE")
				.build()
		);
	}

	@Bean
	SecurityWebFilterChain springSecurity(
		HttpSecurity http
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
