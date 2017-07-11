package de.springbootbuch.reactive.filmstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.MapUserDetailsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsRepository;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsRepository userDetailsRepository() {
		return new MapUserDetailsRepository(
			User.withUsername("spring")
				.password("boot")
				.roles("USER")
				.build(),
			User.withUsername("michael")
				.password("simons")
				.roles("USER")
				.build()
		);
	}
}