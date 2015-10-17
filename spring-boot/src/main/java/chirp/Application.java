package chirp;

import chirp.domain.Message;
import chirp.domain.Status;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.ZoneOffset;

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
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Message.class, new JsonDeserializer<Message>() {
            @Override
            public Message deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                JsonNode node = jp.getCodec().readTree(jp);
                String message = node.get("message").textValue();
                return new Message(message);
            }
        });
        module.addSerializer(Status.class, new JsonSerializer<Status>() {
            @Override
            public void serialize(Status value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeStartObject();
                jgen.writeStringField("name", value.getUser().getName());
                jgen.writeStringField("message", value.getMessage().getText());
                jgen.writeNumberField("time", value.getDateTime().toEpochSecond(ZoneOffset.UTC));
                jgen.writeEndObject();
            }
        });
        return module;
    }
}
