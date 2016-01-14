import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author irof
 */
public class GettingStarted {

    @Test
    public void readValueだねー() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MyValue myValue = mapper.readValue("{\"name\":\"Bob\", \"age\":13}", MyValue.class);

        assertThat(myValue.age).isEqualTo(13);
        assertThat(myValue.name).isEqualTo("Bob");
    }

    @Test
    public void writeValueするよー() throws Exception {
        MyValue myValue = new MyValue();
        myValue.age = 14;
        myValue.name = "John";

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(myValue);

        assertThat(s).isEqualTo("{\"name\":\"John\",\"age\":14}");
    }

    @Test
    public void LocalDateを扱う() throws Exception {
        LocalDate now = LocalDate.now();

        ObjectMapper mapper = new ObjectMapper();
        // ServiceLoaderで自動的にModuleを拾ってくるやつ
        // 一つなら自分で registerModule してもよい
        mapper.findAndRegisterModules();

        String writeValue = mapper.writeValueAsString(now);
        LocalDate readValue = mapper.readValue(writeValue, LocalDate.class);

        // 読み書きして元のと同じならできてるだろー
        assertThat(readValue).isEqualTo(now);
    }
}
