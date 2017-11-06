package de.springbootbuch.reactive.filmstore;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class ReactiveUserDetailsServiceImpl 
	implements ReactiveUserDetailsService {
	@Override
	public Mono<UserDetails> findByUsername(
		String username
	) {
		return Mono.justOrEmpty(
			"tina".equals(username) ? 
				User.withUsername("tina").build() : 
				null
		);
	}
}
