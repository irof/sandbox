package chirp.domain;

/**
 * @author irof
 */
public class Status {
    private final User user;
    private final Message message;

    public Status(User user, Message message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public Message getMessage() {
        return message;
    }
}
