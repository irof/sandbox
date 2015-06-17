package spi;

import org.junit.Test;
import sun.misc.Service;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author irof
 */
public class HelloTest {

    @Test
    public void ファイルに書いたのが全部返ってくる() throws Exception {
        Iterator<HelloSPI> iterator = Service.providers(HelloSPI.class);

        assertThat(iterator.next(), is(instanceOf(HelloHoge.class)));
        assertThat(iterator.next(), is(instanceOf(HelloFuga.class)));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void 呼ぶごとに違うインスタンス() throws Exception {
        Iterator<HelloSPI> iter1 = Service.providers(HelloSPI.class);
        Iterator<HelloSPI> iter2 = Service.providers(HelloSPI.class);

        assertTrue(iter1.next() != iter2.next());
    }

    @Test
    public void 取れてくるのはクラスの直接のインスタンス() throws Exception {
        Iterator<HelloSPI> iterator = Service.providers(HelloSPI.class);

        // 特にDynamicProxyとかじゃなく普通のクラス
        assertTrue(iterator.next().getClass() == HelloHoge.class);
    }

    @Test
    public void 普通に動くよねと() throws Exception {
        Iterator<HelloSPI> iterator = Service.providers(HelloSPI.class);

        assertThat(iterator.next().hello(), is("HOGE"));
    }

    @Test
    public void プロバイダ構成ファイルが無くてもキレたりしない() throws Exception {
        Iterator<ByeSPI> iterator = Service.providers(ByeSPI.class);

        assertFalse(iterator.hasNext());
    }
}
