package core.v3_4;

import org.assertj.core.internal.Throwables;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * スタックトレースの中身を検証する。
 *
 * {@link Throwables#assertHasStackTraceContaining(org.assertj.core.api.AssertionInfo, Throwable, String)}で
 * スタックトレースを文字列化してcontainsしているので、文字列として含むかの検証になります。
 *
 * @author irof
 * @version 3.4.0
 */
public class WithStackTraceContainingTest {

    @Test
    public void test() throws Exception {
        Exception e = new Exception("hoge");
        RuntimeException re = new RuntimeException("fuga", e);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    throw re;
                })
                .withStackTraceContaining("hoge")
                .withStackTraceContaining("fuga")
                // スタックトレースの一部にもマッチする
                .withStackTraceContaining("WithStackTraceContainingTest.test");

        // assertThatThrownBy を使う場合は hasStackTraceContaining
        assertThatThrownBy(() -> {
            throw re;
        }).isInstanceOf(RuntimeException.class)
                .hasStackTraceContaining("WithStackTraceContainingTest.test");
    }
}
