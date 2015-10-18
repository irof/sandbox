package chirp;

import chirp.domain.User;
import chirp.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author irof
 */
@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    {
        users.add(new User(new UserId("id-irof"), "いろふ"));
        users.add(new User(new UserId("id-hoge"), "ほげくん"));
    }

    public Optional<User> find(String id) {
        return users.stream()
                .filter(user -> user.getId().getId().equals(id))
                .findFirst();
    }

    public User get(String id) {
        return find(id).get();
    }

    public void register(User user) {
        users.add(user);
    }

    public void update(User newUser) {
        // なくても今のところは動く
        get(newUser.getId().getId()).setName(newUser.getName());
    }
}
