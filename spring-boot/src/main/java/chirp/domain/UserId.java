package chirp.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * @author irof
 */
public class UserId {

    private final String id;

    public UserId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(id, userId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static UserId generate() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        return new UserId(s);
    }
}
