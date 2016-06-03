package codesmell.transformer.service;

import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import codesmell.transformer.domain.omdb.OmdbMovie;
import codesmell.transformer.rest.MovieParam;
import codesmell.transformer.util.JaxbUtil;
import codesmell.transformer.util.dozer.ConvertFactory;

public class MovieRouteBuilder extends RouteBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRouteBuilder.class);

	public static final String TOP_LEVEL_FIND_MOVIE_ROUTE_URI = "direct:find-movie";
	public static final String FIND_MOVIE_ROUTE_ID = "find-movie-route";
	
	public static final String OMDB_URL = "http4://www.omdbapi.com/";
	
	@Autowired
	ConvertFactory converter;

	@Override
	public void configure() throws Exception {
		onException(HttpOperationFailedException.class)
			.handled(false)
			.end();
		
		from(TOP_LEVEL_FIND_MOVIE_ROUTE_URI).routeId(FIND_MOVIE_ROUTE_ID)
			// process the movie parameters
			// which are in the body 
			// by getting the URL
			.process(exchange -> {
				String url = this.getOmdbUrl(exchange.getIn().getBody(MovieParam.class));
				LOGGER.info("attempting to find movie: " + url);
				exchange.setProperty("omdbUrl", url);
			})
			// call the OMDB service
			// but first clear out headers and body
			.removeHeaders("*")
			.process(exchange -> {
				exchange.getIn().setBody(null);
			})
			.setHeader(Exchange.HTTP_METHOD, constant("GET"))
			.toD("${property.omdbUrl}")
			.process(exchange -> {
				String xmlResponse = exchange.getIn().getBody(String.class);
				ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponse.getBytes());
				OmdbMovie om = (OmdbMovie) JaxbUtil.getInstance(OmdbMovie.class, bais);
				exchange.getIn().setBody(om);
			})
			// convert the OMDB document
			// which is now in the body
			.bean(converter, "convert(${body}")
			.end();

	}

	public String getOmdbUrl(MovieParam params) {
		StringBuilder sb = new StringBuilder(OMDB_URL);
		sb.append("?r=xml&plot=short");
		
		if (params!=null) {
			if (!StringUtils.isEmpty(params.getTitle())) {
				sb.append("&t=");
				sb.append(params.getTitle());
			}
		}
		
		try {
            URIBuilder uriBuilder = new URIBuilder(sb.toString());
            return uriBuilder.toString();
        } catch (URISyntaxException e) {
            LOGGER.error("Bad URL",e);
            return null;
        }
	}
}
