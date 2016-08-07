package core.v3_0;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * lambda式による例外検証構文。
 *
 * 現在のJUnitによる例外検証で最も洗練されているのは `@Test(expected = ...)` だと思うけれど、
 * アノテーションの制約上できる検証に限りはある。
 * `ExpectedException`ルールはそこそこよかったのだけれど、大半のメソッドが例外の検証でない場合に、
 * クラス全体にかかるルールを定義するのは微妙感があった。
 * と言うことで、この形式の例外検証は現時点での理想的妥協点だと思う。
 *
 * @author irof
 * @version 3.0.0
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-3.0.0-thrown-by">Java 8 friendly exceptions/throwables testing</a>
 */
public class ExceptionTest {

    @Test
    public void lambdaを使用した標準的な例外検証() throws Exception {
        // JUnitの`ExpectedException`に比べると洗練されている感はある。
        // 従来のtry-catchによる検証と構文の複雑さはトントンな気はする。
        // 例外が発生しない場合のミスが無い点を利点としておくべきか。

        assertThatThrownBy(() -> {
            throw new RuntimeException("hello, hoge world!");
        }).isInstanceOf(RuntimeException.class)
                .hasMessageStartingWith("hello")
                .hasMessageContaining("hoge")
                .hasMessageEndingWith("world!");
    }

    @Test
    public void 例外を一時変数に入れてあとから検証する() throws Exception {
        // exerciseとassertionを分けるスタイル。
        // assertionには`AbstractThrowableAssert`が使われるので、機能的には同等。
        // ただし、例外が起こらない場合、こちらでは`throwable`には`null`が入っている。
        // `assertThatThrownBy`では例外が起こらなかった旨が通知されるので、機能的にはこちらのほうが劣るか。

        Throwable throwable = catchThrowable(() -> {
            throw new RuntimeException("hello, hoge world!");
        });

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageStartingWith("hello")
                .hasMessageContaining("hoge")
                .hasMessageEndingWith("world!");
    }
}
