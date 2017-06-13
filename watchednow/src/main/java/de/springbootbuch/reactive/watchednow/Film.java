package de.springbootbuch.reactive.watchednow;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class Film {
	private final String id;
	
	private final String title;

	public Film(String id, String title) {
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
