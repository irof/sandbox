package chirp;

import chirp.domain.Message;
import chirp.domain.Status;
import chirp.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author irof
 */
@Repository
public class StatusRepository {

    private List<Status> list = new ArrayList<>();

    {
        list.add(new Status(new User("irof"), new Message("今日は1日立ち話ばかりしてたのですごく疲れました。"), LocalDateTime.parse("2015-10-16T18:24:15")));
        list.add(new Status(new User("irof"), new Message("そろそろ来年を振り返るか"), LocalDateTime.parse("2015-10-16T18:04:15")));
    }

    public List<Status> getPublic() {
        return list;
    }

    public void add(Status status) {
        list.add(status);
    }
}
