package sample.services;

import sample.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/customers")
public class CustomerResource {

    private Map<Integer, Customer> db = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    @POST
    @Consumes("application/xml")
    public Response createCustomer(Customer customer) {
        customer.id = counter.incrementAndGet();
        db.put(customer.id, customer);
        System.out.println("Created customer " + customer.id);
        return Response.created(URI.create("/customers/" + customer.id)).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public Customer getCustomer(@PathParam("id") int id) {
        Customer customer = db.get(id);
        if (customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return customer;
    }
}
