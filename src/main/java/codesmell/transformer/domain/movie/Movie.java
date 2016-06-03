package codesmell.transformer.domain.movie;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Movie")
public class Movie {

	private String movieTitle;
	private String rating;
	private String releaseYear;
	
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	
}
