package misc;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class KeyCollectTest {

    private KeyCollect sut = new KeyCollect();

    @Test
    public void 元データが何もないなら空のリスト() throws Exception {
        List<KeyCollect.Input> data = Collections.emptyList();
        Collection<KeyCollect.Output> result = sut.collectByCode(data);
        assertThat(result, is(emptyCollectionOf(KeyCollect.Output.class)));
    }

    @Test
    public void 元データが1件なら1件ののリスト() throws Exception {
        List<KeyCollect.Input> data = Collections.singletonList(new KeyCollect.Input("A01", "hoge", 100));
        Collection<KeyCollect.Output> result = sut.collectByCode(data);
        assertThat(result, is(Collections.singletonList(new KeyCollect.Output("A01", 100))));
    }

    @Test
    public void 同じキーの値が集計される() throws Exception {
        List<KeyCollect.Input> data = Arrays.asList(
                new KeyCollect.Input("A01", "hoge", 100),
                new KeyCollect.Input("A01", "piyo", 200),
                new KeyCollect.Input("A02", "hoge", 300),
                new KeyCollect.Input("A03", "hoge", 400),
                new KeyCollect.Input("A03", "piyo", 500));

        Collection<KeyCollect.Output> result = sut.collectByCode(data);
        assertThat(result, is(Arrays.asList(
                new KeyCollect.Output("A01", 300),
                new KeyCollect.Output("A02", 300),
                new KeyCollect.Output("A03", 900)
        )));
    }
}