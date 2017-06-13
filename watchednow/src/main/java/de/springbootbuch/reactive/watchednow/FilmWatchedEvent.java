package de.springbootbuch.reactive.watchednow;

import java.time.ZonedDateTime;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Document(collection = "film-watched-events")
public class FilmWatchedEvent {
	private String id;

	private final String filmId;

	private final String title;

	private final ZonedDateTime watchedOn;

	public FilmWatchedEvent(Film film) {
		this.filmId = film.getId();
		this.title = film.getTitle();
		this.watchedOn = ZonedDateTime.now();
	}

	public String getId() {
		return id;
	}

	public String getFilmId() {
		return filmId;
	}

	public String getTitle() {
		return title;
	}

	public ZonedDateTime getWatchedOn() {
		return watchedOn;
	}
}
