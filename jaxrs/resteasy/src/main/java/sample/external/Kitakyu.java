package sample.external;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import sample.Station;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * @author irof
 */
@Path("kitakyu")
@Produces(MediaType.APPLICATION_XML)
public class Kitakyu {

    private final String BASE_URL = "http://localhost:18080/jersey";

    @GET
    public Collection<Station> get() {
        return ClientBuilder.newClient()
                .target(BASE_URL).path("kitakyu/stations")
                .request()
                .get(new GenericType<Collection<Station>>() {
                });
    }


    @GET
    @Path("{number}")
    public Station get(@PathParam("number") String number) {
        return ClientBuilder.newClient()
                .target(BASE_URL).path("kitakyu/stations").path(number)
                .queryParam("number", number)
                .request()
                .get(Station.class);
    }

    @GET
    @Path("proxy")
    public Collection<Station> getWithProxy() {
        return new ResteasyClientBuilder().build().target(BASE_URL)
                .proxy(JerseyServiceProxy.class)
                .stations();
    }

    @Path("kitakyu")
    interface JerseyServiceProxy {
        @GET
        @Path("stations")
        @Produces(MediaType.APPLICATION_XML)
        Collection<Station> stations();
    }
}
