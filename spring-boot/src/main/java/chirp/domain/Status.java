package chirp.domain;

import java.time.LocalDateTime;

/**
 * @author irof
 */
public class Status {
    private final User user;
    private final Message message;
    private final LocalDateTime dateTime;

    public Status(User user, Message message, LocalDateTime dateTime) {
        this.user = user;
        this.message = message;
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public Message getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
