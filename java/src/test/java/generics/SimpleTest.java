package generics;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author irof
 */
public class SimpleTest {

    @Test
    public void 型変数名をとってくる() throws Exception {
        class Hoge<T1, T2> {
        }

        Class<Hoge> clz = Hoge.class;

        // この定義だと名前が取れるくらい
        assertThat(clz.getTypeParameters())
                .extracting(TypeVariable::getName)
                .containsExactly("T1", "T2");
    }

    @Test
    public void 型変数にバインドされた型をとってくる() throws Exception {
        class Hoge<T1, T2> {
        }

        // バインドした無名クラスからとってみる
        Hoge<String, LocalDate> hoge = new Hoge<String, LocalDate>() {
        };
        Class<?> clz = hoge.getClass();

        ParameterizedType parameterizedType = (ParameterizedType) clz.getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        assertThat(actualTypeArguments).containsExactly(String.class, LocalDate.class);
    }
}
