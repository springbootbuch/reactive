package de.springbootbuch.reactive.filmstore;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class Actor {
	private final String firstName;
	
	private final String lastName;

	@JsonCreator
	public Actor(
		@JsonProperty("firstName") String firstName, 
		@JsonProperty("lastName") String lastName
	) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
}