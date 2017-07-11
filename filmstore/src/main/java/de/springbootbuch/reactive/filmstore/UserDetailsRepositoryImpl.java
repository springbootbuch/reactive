package de.springbootbuch.reactive.filmstore;

import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsRepository;
import reactor.core.publisher.Mono;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class UserDetailsRepositoryImpl 
	implements UserDetailsRepository 
{
	@Override
	public Mono<UserDetails> findByUsername(
		String username
	) {
		return Mono.justOrEmpty(
			Optional.ofNullable(
				"tina".equals(username) ? 
					User.withUsername("tina").build() : 
					null)
		);
	}
}
