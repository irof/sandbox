package core.v3_0;

import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * `Predicate`を使用した`matches`メソッドによる検証。
 *
 * 任意の検証条件を作成する `Condition` があるが、匿名クラスでの実現になっている。
 * インタフェースではなくクラスのため直接Lambda式にはできないので、別メソッドの形で提供された。
 *
 * @author irof
 * @version assertj-core:3.0.0
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-3.0.0-matches-assertions">Add matches assertion with Predicate parameter</a>
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-3.0.0-condition-predicate-support">Condition can be defined with Predicate</a>
 */
public class MatchesTest {

    @Test
    public void Predicateを検証条件とするmatchesが追加された() throws Exception {
        Object obj = new Object();

        assertThat(obj).matches(value -> value != null);
    }

    @Test(expected = AssertionError.class)
    public void 検証エラーメッセージのカスタマイズ() throws Exception {
        Object obj = new Object();

        // エラー時
        // 第二引数に何も設定しなかった場合は「設定したほうがいいよ」的なメッセージが出る。
        // 正直なところコードを見れば検証できるとは思うので、無理に書く必要はない。
        // とはいえ「デフォルトのエラーメッセージ」は結構ダサいので書いたほうが良いかも。
        assertThat(obj).matches(value -> value == null, "valueはnullである");
    }

    @Test
    public void 参考_v2以前だとConditionを使用する() throws Exception {
        Object obj = new Object();

        // コンストラクタ引数は検証エラーメッセージの期待値に使用される。
        assertThat(obj).is(new Condition<Object>("not null") {
            @Override
            public boolean matches(Object value) {
                return value != null;
            }
        });
    }

    @Test
    public void Conditionの構築にPredicateを使用できるようになった() throws Exception {
        Object obj = new Object();

        // けどこれ使うならmatches使用するだろうなー
        assertThat(obj).is(new Condition<>(Objects::nonNull, "not null"));
    }
}
