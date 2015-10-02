package sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author irof
 */
@Path("misc")
public class Misc {

    @GET
    @Path("exception")
    public void exception() throws Exception {
        throw new Exception("");
    }
}
