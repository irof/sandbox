package chirp.domain;

/**
 * @author irof
 */
public class User {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
