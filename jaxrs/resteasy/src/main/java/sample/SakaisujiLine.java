package sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author irof
 */
@Path("sakaisuji")
public class SakaisujiLine {

    private static final Map<String, Station> stations;

    static {
        stations = Stream.of(
                new Station("K11", "天神橋筋六丁目駅"),
                new Station("K12", "扇町駅"),
                new Station("K13", "南森町駅"),
                new Station("K14", "北浜駅"),
                new Station("K15", "堺筋本町駅"),
                new Station("K16", "長堀橋駅"),
                new Station("K17", "日本橋駅"),
                new Station("K18", "恵美須町駅"),
                new Station("K19", "動物園前駅"),
                new Station("K20", "天下茶屋駅"))
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
