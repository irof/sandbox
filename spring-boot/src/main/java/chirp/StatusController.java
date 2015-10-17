package chirp;

import chirp.domain.Message;
import chirp.domain.Status;
import chirp.domain.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author irof
 */
@RestController
@RequestMapping("status")
public class StatusController {

    @Autowired
    TimelineRepository repository;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> status(@RequestBody Message message) {
        repository.add(new Status(User.ANONYMOUS, message));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Bean
    public com.fasterxml.jackson.databind.Module module() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Message.class, new JsonDeserializer<Message>() {
            @Override
            public Message deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                JsonNode node = jp.getCodec().readTree(jp);
                String message = node.get("message").textValue();
                return new Message(message);
            }
        });
        return simpleModule;
    }
}
