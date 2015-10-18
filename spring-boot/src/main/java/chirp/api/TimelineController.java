package chirp.api;

import chirp.StatusRepository;
import chirp.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author irof
 */
@RestController
@RequestMapping("api/timeline")
public class TimelineController {

    @Autowired
    StatusRepository repository;

    @RequestMapping
    public List<Status> publicTimeline() {
        return repository.getPublic();
    }
}
