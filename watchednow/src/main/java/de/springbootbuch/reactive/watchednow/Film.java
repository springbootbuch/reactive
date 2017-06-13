package de.springbootbuch.reactive.watchednow;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class Film {
	private final String id;
	
	private final String title;

	@JsonCreator
	public Film(@JsonProperty("id") String id, @JsonProperty("title") String title) {
		this.id = id;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
}
