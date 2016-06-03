package codesmell.transformer.domain.omdb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.NONE)
public class OmdbMovie {

	@XmlElement(name="movie")
	private OmdbMovieDetail movieDetail;

	public OmdbMovieDetail getMovieDetail() {
		return movieDetail;
	}

	public void setMovieDetail(OmdbMovieDetail movieDetail) {
		this.movieDetail = movieDetail;
	}
	
	
}
