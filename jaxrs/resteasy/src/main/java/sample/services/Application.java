package sample.services;

import java.util.HashSet;
import java.util.Set;

public class Application extends javax.ws.rs.core.Application {

    private Set<Object> singletons = new HashSet<>();

    public Application() {
        singletons.add(new CustomerResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
