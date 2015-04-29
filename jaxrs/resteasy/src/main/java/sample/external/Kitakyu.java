package sample.external;

import sample.Station;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * @author irof
 */
@Path("kitakyu")
public class Kitakyu {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Collection<Station> get() {
        return ClientBuilder.newClient()
                .target("http://localhost:18080/jersey/kitakyu/stations")
                .request()
                .get(new GenericType<Collection<Station>>() {
                });
    }
}
