package de.springbootbuch.reactive.watchednow;

import java.time.LocalDateTime;
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

	private String filmId;

	private String title;

	private LocalDateTime watchedOn;

	public FilmWatchedEvent() {
	}
	
	public FilmWatchedEvent(Film film) {
		this.filmId = film.getId();
		this.title = film.getTitle();
		this.watchedOn = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getWatchedOn() {
		return watchedOn;
	}

	public void setWatchedOn(LocalDateTime watchedOn) {
		this.watchedOn = watchedOn;
	}	
}
