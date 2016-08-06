package core.v3_5;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * satisfiesメソッドによる検証。
 *
 * {@link java.util.function.Predicate}を使用するmatchesメソッドがv3.0.0で追加されたが、
 * こちらは{@link java.util.function.Consumer}を与える。
 *
 * Consumerは値を返さないので、検証を記述する形。
 * 複数の検証をまとめて行いたいならばこちらになる？
 *
 * @author irof
 * @version 3.5.0
 */
public class SatisfiesTest {

    @Test
    public void 検証のまとまりをConsumerで記述する() throws Exception {
        Consumer<String> requirements = value -> {
            assertThat(value.length()).isEqualTo(4);
            assertThat(value.indexOf('E')).isEqualTo(3);
        };

        String value = "HOGE";
        assertThat(value).satisfies(requirements);

        // シンプルな条件であれば普通にassertThatを並べるほうが良い。
        // 別にConsumerの中に並べて書いたからと言って、全ての検証エラーを報告してくれるわけではない。
        // （構文から自明だが、一つエラーになったらそこで修了。）

        // 複数のオブジェクトに対して同じ検証を行いたいならば出番はあるかもだが、
        // それらのオブジェクトは通常コレクションには入っているだろう。
        // Assertionsを作成する代わりに使えなくもないけれど、筋は悪そう。
    }

    @Test
    public void 一時変数を保持しなくてよくなる() throws Exception {
        List<String> list = Arrays.asList("Fizz", "Buzz", "FizzBuzz");

        // こちらの用途のほうが出番が多そう
        assertThat(list.get(2)).satisfies(value -> {
            assertThat(value.length()).isEqualTo(8);
            assertThat(value.indexOf('B')).isEqualTo(4);
        });

        // 基本的にはメソッドチェーンで検証したいところだが、いつも欲しい検証メソッドがあるわけではない。
        // Assertionsを作成するにしても、検証対象の取得方法が違ったり、条件が複雑だったりすると、やりづらくなる。

        // いつもこんな優しい世界ではないのだ。
        assertThat(list.get(2))
                .hasSize(8)
                .isNotEmpty();

        // これまでは、ベタ書きするか一時変数に保持してきた。
        String value = list.get(2);
        assertThat(value.length()).isEqualTo(8);
        assertThat(value.indexOf('B')).isEqualTo(4);

        // この程度の検証だと、satisfiesを使う方が文字数は多くなる。
        // 特に挙動は変わらないし、特に検証エラーメッセージがわかりやすくなったりもしないし、
        // 「常にsatisfiesを使用する方がいい」とは言えない。
    }
}
