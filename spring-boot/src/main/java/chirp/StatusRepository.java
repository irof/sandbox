package chirp;

import chirp.domain.Message;
import chirp.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author irof
 */
@Repository
public class StatusRepository {

    @Autowired
    UserRepository users;

    private List<Status> list = new ArrayList<>();

    @PostConstruct
    public void setup() {
        list.add(new Status(users.get("id-irof"), new Message("今日は1日立ち話ばかりしてたのですごく疲れました。"), LocalDateTime.parse("2015-10-16T18:24:15")));
        list.add(new Status(users.get("id-irof"), new Message("そろそろ来年を振り返るか"), LocalDateTime.parse("2015-10-16T18:04:15")));
    }

    public List<Status> getPublic() {
        return list;
    }

    public void add(Status status) {
        list.add(status);
    }
}
