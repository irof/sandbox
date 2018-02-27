package spi;

import org.junit.Test;
import sun.misc.Service;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author irof
 */
public class HelloTest {

    @Test
    public void ファイルに書いたのが全部返ってくる() throws Exception {
        Iterator<HelloSPI> iterator = Service.providers(HelloSPI.class);

        assertThat(iterator)
                .hasSize(2)
                .extracting(element -> element.getClass().getCanonicalName())
                .containsExactlyInAnyOrder(
                        HelloHoge.class.getCanonicalName(),
                        HelloFuga.class.getCanonicalName());
    }

    @Test
    public void 呼ぶごとに違うインスタンス() throws Exception {
        Iterator<HelloSPI> iter1 = Service.providers(HelloSPI.class);
        Iterator<HelloSPI> iter2 = Service.providers(HelloSPI.class);

        assertThat(iter1.next()).isNotSameAs(iter2.next());
    }

    @Test
    public void 普通に動くよねと() throws Exception {
        Iterator<HelloSPI> iterator = Service.providers(HelloSPI.class);

        HelloSPI instance = iterator.next();
        assertThat(instance.hello()).isEqualTo("HOGE");
    }

    @Test
    public void プロバイダ構成ファイルが無くてもキレたりしない() throws Exception {
        Iterator<ByeSPI> iterator = Service.providers(ByeSPI.class);

        assertThat(iterator).isEmpty();
    }
}
