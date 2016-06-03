package codesmell.transformer.util.dozer;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import codesmell.transformer.domain.movie.Movie;
import codesmell.transformer.domain.omdb.OmdbMovie;

public class ConvertFactory {
	public Movie convert(OmdbMovie omdbMovie) {
		return ConvertFactory.getDozerMapperInstance().map(omdbMovie, Movie.class);
	}

	public static Mapper getDozerMapperInstance() {
		return DozerBeanMapperSingletonWrapper.getInstance();
	}
}
