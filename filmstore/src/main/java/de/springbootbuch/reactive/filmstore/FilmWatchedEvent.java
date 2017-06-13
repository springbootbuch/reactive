package de.springbootbuch.reactive.filmstore;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class FilmWatchedEvent {
	private final String title;
	
	@JsonCreator
	public FilmWatchedEvent(
		@JsonProperty("title") String title
	) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
