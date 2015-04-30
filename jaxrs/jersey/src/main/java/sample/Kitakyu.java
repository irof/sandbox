package sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("kitakyu")
public class Kitakyu {

    static final Map<String, Station> stations;

    static {
        stations = Arrays.asList(
                new Station("M08", "千里中央"),
                new Station("M09", "桃山台"),
                new Station("M10", "緑地公園"),
                new Station("M11", "江坂"))
                .stream()
                .collect(Collectors.toMap(Station::getNumber, Function.identity()));
    }

    @GET
    @Path("stations")
    @Produces(MediaType.APPLICATION_XML)
    public Collection<Station> stations() {
        return stations.values();
    }

    @GET
    @Path("stations/{number}")
    @Produces(MediaType.APPLICATION_XML)
    public Station stations(@PathParam("number") String number) {
        return stations.get(number);
    }
}
