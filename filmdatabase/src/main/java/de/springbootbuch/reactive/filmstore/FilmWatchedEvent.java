package de.springbootbuch.reactive.filmstore;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class FilmWatchedEvent {
	private String id;
	
	private final String title;
	
	private final long duration;

	public FilmWatchedEvent(Film film, long duration) {
		this.id = film.getId();
		this.title = film.getTitle();
		this.duration = duration;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public long getDuration() {
		return duration;
	}
}
