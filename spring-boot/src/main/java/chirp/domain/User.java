package chirp.domain;

/**
 * @author irof
 */
public class User {

    public static final User ANONYMOUS = new User(UserId.generate(), "あのにます");

    private final UserId id;
    private String name;

    public User(UserId id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserId getId() {
        return id;
    }
}
