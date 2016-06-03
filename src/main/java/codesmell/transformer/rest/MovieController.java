package codesmell.transformer.rest;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesmell.transformer.domain.movie.Movie;
import codesmell.transformer.service.MovieRouteBuilder;

@RestController
public class MovieController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

	@Produce(uri = MovieRouteBuilder.TOP_LEVEL_FIND_MOVIE_ROUTE_URI)
	private ProducerTemplate camelProducer;

	@RequestMapping(value = "findMovie", 
		method = RequestMethod.GET, 
		produces = MediaType.APPLICATION_XML_VALUE)
	public Movie findMovie(
		@RequestParam("title") String movieTitle,
		@RequestParam(name = "imdbId", required = false) String id) {

		LOGGER.debug("processing find movie request...");

		MovieParam params = new MovieParam();
		params.setTitle(movieTitle);
		params.setImdbId(id);
		Movie resp = (Movie) camelProducer.requestBody(params, Movie.class);

		// that's no good...
		if (resp == null) {
			LOGGER.warn("nothing available to return!");
		}
		return resp;
	}
}
