package codesmell.transformer.domain.omdb;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import codesmell.transformer.domain.movie.Movie;
import codesmell.transformer.util.JaxbUtil;
import codesmell.transformer.util.dozer.ConvertFactory;

public class OmdbMovieTest {

	@Test
	public void test_to_xml() throws Exception {
		OmdbMovie movie = new OmdbMovie();
		OmdbMovieDetail detail = new OmdbMovieDetail();
		detail.setMovieTitle("Ghostbusters");
		detail.setRating("PG");
		detail.setReleaseYear("1984");
		detail.setRunTime("106 min");
		movie.setMovieDetail(detail);
		
		String xmlDoc = new String(JaxbUtil.toXml(movie));
		
		assertEquals("<root><movie title=\"Ghostbusters\" rated=\"PG\" year=\"1984\" runtime=\"106 min\"/></root>", xmlDoc);
	}

	@Test
	public void test_to_java() throws Exception {
		String xmlDoc = "<root><movie title=\"Ghostbusters\" rated=\"PG\" year=\"1984\" runtime=\"106 min\"/></root>";
		
		OmdbMovie movie = (OmdbMovie) JaxbUtil.getInstance(OmdbMovie.class, new ByteArrayInputStream(xmlDoc.getBytes()));
		assertNotNull(movie);
		
		OmdbMovieDetail detail = movie.getMovieDetail();
		assertNotNull(detail);
		assertEquals("Ghostbusters", detail.getMovieTitle());
		assertEquals("PG", detail.getRating());
		assertEquals("1984", detail.getReleaseYear());
		assertEquals("106 min", detail.getRunTime());
	}
	
	@Test
	public void test_to_movie() throws Exception {
		OmdbMovie movie = new OmdbMovie();
		OmdbMovieDetail detail = new OmdbMovieDetail();
		detail.setMovieTitle("Ghostbusters");
		detail.setRating("PG");
		detail.setReleaseYear("1984");
		detail.setRunTime("106 min");
		movie.setMovieDetail(detail);
		
		ConvertFactory converter = new ConvertFactory();
		Movie movieResult = converter.convert(movie);
		
		assertNotNull(movieResult);
		assertEquals("Ghostbusters", movieResult.getMovieTitle());
		assertEquals("PG", movieResult.getRating());
		assertEquals("1984", movieResult.getReleaseYear());
	}
	
}
