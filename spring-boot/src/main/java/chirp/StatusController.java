package chirp;

import chirp.domain.Message;
import chirp.domain.Status;
import chirp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author irof
 */
@RestController
@RequestMapping("status")
public class StatusController {

    @Autowired
    StatusRepository repository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> status(@RequestBody Message message,
                                    @RequestHeader(value = "X-Chirp-User-Id", required = false) String id) {
        User user = userRepository.find(id).orElse(User.ANONYMOUS);
        repository.add(new Status(user, message, LocalDateTime.now()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
