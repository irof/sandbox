package collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author irof
 */
@RunWith(Parameterized.class)
public class KeyCollectTest {

    @Parameterized.Parameters(name = "{index} {0}")
    public static Collection<Method> factoryMethods() {
        return Arrays.stream(KeyCollect.class.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(KeyCollect.FactoryMethod.class))
                .collect(Collectors.toList());
    }

    @Parameterized.Parameter
    public Method factoryMethod;

    private KeyCollect sut;

    @Before
    public void setup() throws Exception {
        sut = (KeyCollect) factoryMethod.invoke(null);
    }

    @Test
    public void 元データが何もないなら空のリスト() throws Exception {
        List<KeyCollect.Input> data = Collections.emptyList();
        Collection<KeyCollect.Output> result = sut.collectByKey(data);

        assertThat(result).isEmpty();
    }

    @Test
    public void 元データが1件なら1件ののリスト() throws Exception {
        List<KeyCollect.Input> data = Collections.singletonList(new KeyCollect.Input("A01", "hoge", 100));
        Collection<KeyCollect.Output> result = sut.collectByKey(data);

        assertThat(result).containsExactly(new KeyCollect.Output("A01", 100));
    }

    @Test
    public void 同じキーの値が集計される() throws Exception {
        List<KeyCollect.Input> data = Arrays.asList(
                new KeyCollect.Input("A01", "hoge", 100),
                new KeyCollect.Input("A01", "piyo", 200),
                new KeyCollect.Input("A02", "hoge", 300),
                new KeyCollect.Input("A03", "hoge", 400),
                new KeyCollect.Input("A03", "piyo", 500));

        Collection<KeyCollect.Output> result = sut.collectByKey(data);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        new KeyCollect.Output("A01", 300),
                        new KeyCollect.Output("A02", 300),
                        new KeyCollect.Output("A03", 900)
                );
    }
}