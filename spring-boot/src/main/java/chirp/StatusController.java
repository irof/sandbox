package chirp;

import chirp.domain.Message;
import chirp.domain.Status;
import chirp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author irof
 */
@RestController
@RequestMapping("status")
public class StatusController {

    @Autowired
    StatusRepository repository;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> status(@RequestBody Message message) {
        repository.add(new Status(User.ANONYMOUS, message));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
