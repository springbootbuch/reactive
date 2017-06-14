package de.springbootbuch.reactive.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class Film {
	
	private String id;
	
	private final String title;
	
	private final Integer releaseYear;
	
	@JsonCreator
	public Film(
		@JsonProperty("title") String title, 
		@JsonProperty("releaseYear") Integer releaseYear
	) {
		this.title = title;
		this.releaseYear = releaseYear;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}
}
