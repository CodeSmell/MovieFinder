package codesmell.transformer;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import codesmell.transformer.service.MovieRouteBuilder;
import codesmell.transformer.util.dozer.ConvertFactory;

@Configuration
public class MovieConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieConfig.class);
    
    @Autowired
    CamelContext camelContext;

    @Bean
    public ConvertFactory buildConvertFactory(){
    	return new ConvertFactory();
    }
    
    @Bean
    public MovieRouteBuilder buildMovieRouteBuilder() {
        LOGGER.debug("building MovieRouteBuilder");
        return new MovieRouteBuilder();
    }
    
}