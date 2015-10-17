package chirp;

import chirp.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author irof
 */
@RestController
@RequestMapping("timeline")
public class Timeline {

    @Autowired
    TimelineRepository repository;

    @RequestMapping
    @ResponseBody
    public List<TextStatus> publicTimeline() {
        return repository.getPublic()
                .stream()
                .map(TextStatus::new)
                .collect(toList());
    }

    /**
     * {"userName":"irof","message":"ほげほげ"} みたいな形にする子。
     * Statusの方にアノテーションとかつけたくないので。
     */
    public static class TextStatus {
        private final Status status;

        TextStatus(Status status) {
            this.status = status;
        }

        public String getUserName() {
            return status.getUser().getName();
        }

        public String getMessage() {
            return status.getMessage().getText();
        }
    }
}
