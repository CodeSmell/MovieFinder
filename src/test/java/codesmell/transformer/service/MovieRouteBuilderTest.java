package codesmell.transformer.service;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import codesmell.transformer.MovieConfig;
import codesmell.transformer.TestConfig;
import codesmell.transformer.domain.movie.Movie;
import codesmell.transformer.rest.MovieParam;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, MovieConfig.class },
        loader = AnnotationConfigContextLoader.class)
public class MovieRouteBuilderTest extends CamelTestSupport {

	@Autowired
    MovieRouteBuilder movieRouteBuilder;

    @Produce(uri = MovieRouteBuilder.TOP_LEVEL_FIND_MOVIE_ROUTE_URI)
    private ProducerTemplate camelProducer;
    
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        context.addRoutes(movieRouteBuilder);
    }
    
    @Test
    public void testFindMovie() throws Exception {
        
        MovieParam mp = new MovieParam();
        mp.setTitle("Ghostbusters");
        
        StringBuilder xmlResponse = new StringBuilder();
        xmlResponse.append("<root response=\"True\">");
        xmlResponse.append("<movie title=\"Ghostbusters\" year=\"1984\" rated=\"PG\" />");
        xmlResponse.append("</root>");
        
        
        // Mock the API call
        context.getRouteDefinition(MovieRouteBuilder.FIND_MOVIE_ROUTE_ID).adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint(movieRouteBuilder.getOmdbUrl(mp))
                        .skipSendToOriginalEndpoint()
                        .to("mock:apiCall")
                            .process(exchange -> {
                                exchange.getOut().setBody(xmlResponse.toString());
                            });
            }
        });

        MockEndpoint mockActivationEndpoint = getMockEndpoint("mock:apiCall");
        mockActivationEndpoint.setExpectedMessageCount(1);

        Movie movieResponse = camelProducer.requestBody(mp, Movie.class);
        
        assertNotNull(movieResponse);
        assertEquals("Ghostbusters", movieResponse.getMovieTitle());
        assertEquals("PG", movieResponse.getRating());
        assertEquals("1984", movieResponse.getReleaseYear());

    }
    
}
