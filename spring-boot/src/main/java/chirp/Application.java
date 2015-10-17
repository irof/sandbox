package chirp;

import chirp.domain.Message;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * @author irof
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
