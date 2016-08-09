package core.v1;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Classオブジェクトに対する検証。
 *
 * どの程度出番があるかは疑問だけれど、知っていたら使いそうな場面はなくはなさそう。
 *
 * @author irof
 * @version 1.3.0
 */
public class ClassAssertionTest {

    @Test
    public void フィールドの有無を検証できる() throws Exception {
        // リフレクションに依存したコードがあるときのガード的に使うとかかな
        assertThat(String.class)
                .hasDeclaredFields("value", "hash");
    }

    @Test
    public void アノテーションの検証() throws Exception {
        // フレームワーク上で規定されているものの検証かな
        assertThat(Function.class)
                .hasAnnotation(FunctionalInterface.class);
    }
}
