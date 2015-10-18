package chirp.domain;

/**
 * @author irof
 */
public class User {
    private final UserId id;
    private final String name;

    public User(UserId id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static final User ANONYMOUS = new User(UserId.generate(), "あのにます");

    public UserId getId() {
        return id;
    }
}
