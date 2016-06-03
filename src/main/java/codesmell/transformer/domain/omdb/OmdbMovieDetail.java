package codesmell.transformer.domain.omdb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="movie")
public class OmdbMovieDetail {
	
	private String movieTitle;
	private String rating;
	private String releaseYear;
	private String runTime;
	
	@XmlAttribute(name = "title")
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	@XmlAttribute(name = "rated")
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	@XmlAttribute(name = "year")
	public String getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}
	@XmlAttribute(name = "runtime")
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	
}
