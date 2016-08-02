package sample;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author irof
 */
public class MappingTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void 項目が足りない() throws Exception {
        Hoge hoge = mapper.readValue("{}", Hoge.class);

        assertThat(hoge.getName()).isNull();
        assertThat(hoge.getAge()).isZero();
    }

    @Test
    public void 項目が一致() throws Exception {
        Hoge hoge = mapper.readValue("{\"name\":\"HOGE\",\"age\":19}", Hoge.class);

        assertThat(hoge.getName()).isEqualTo("HOGE");
        assertThat(hoge.getAge()).isEqualTo(19);
    }

    @Test
    public void 項目が多い() throws Exception {
        // 知らない子がいても無視する設定。
        // これがないとUnrecognizedPropertyExceptionになる。
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Hoge hoge = mapper.readValue("{\"name\":\"HOGE\",\"age\":19,\"xxx\":0}", Hoge.class);

        assertThat(hoge.getName()).isEqualTo("HOGE");
        assertThat(hoge.getAge()).isEqualTo(19);
    }
}
