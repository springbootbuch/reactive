package de.springbootbuch.reactive.watchednow.support;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public abstract class FilmConstructorMixin {

	FilmConstructorMixin(@JsonProperty("id") String a, @JsonProperty("title") String b) {
	}
}
