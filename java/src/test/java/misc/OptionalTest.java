package misc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static matchers.IsOptional.isEmpty;
import static matchers.IsOptional.isValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * @author irof
 * @see Optional
 */
@RunWith(Enclosed.class)
public class OptionalTest {

    /**
     * Optionalを生成する3つの方法。
     * だいたい <code>ofNullable</code> で事足りる。
     *
     * @see Optional#ofNullable(Object)
     * @see Optional#of(Object)
     * @see Optional#empty()
     */
    public static class FactoryMethod {

        @Test
        public void of() throws Exception {
            Optional<String> sut = Optional.of("hoge");
            assertThat(sut, isValue("hoge"));
        }

        @Test(expected = NullPointerException.class)
        public void of_nullを渡すと例外() throws Exception {
            // nullでない値をOptionalにしたい場合がどれだけあるか？
            // 例えば、インタフェースを合わせるためならわからなくはない。
            // nullでないことがわかっているときに ofNullable を使ってしまうと
            // nullに気づくのが遅れるので、それよりはいいのかも。
            Optional.of(null);
        }

        @Test
        public void empty() throws Exception {
            Optional<?> sut = Optional.empty();
            assertThat(sut, isEmpty());
        }

        @Test
        public void ofNullable() throws Exception {
            // nullでない限りはofと同じ挙動
            Optional<String> sut = Optional.ofNullable("hoge");
            assertThat(sut, isValue("hoge"));
        }

        @Test
        public void ofNullable_nullを渡すとempty() throws Exception {
            // nullのときはemptyと同じ挙動
            Optional<Object> sut = Optional.ofNullable(null);
            assertThat(sut, isEmpty());

            // nullを渡すとemptyと同じインスタンスが使われる
            // ……のは実装の詳細で、たまたま。
            // だから、こんなコードを書いてはいけない。
            // assertTrue(sut == Optional.empty());
        }
    }

    /**
     * Optionalの値を取り出したり使ったりするもの。
     * この使い方を一通り押さえておけば困らない。
     *
     * @see Optional#get()
     * @see Optional#orElse(Object)
     * @see Optional#orElseGet(Supplier)
     * @see Optional#orElseThrow(Supplier)
     * @see Optional#ifPresent(Consumer)
     * @see Optional#isPresent()
     */
    public static class UseValues {

        @Test
        public void get() throws Exception {
            Object value = new Object();
            Optional<Object> sut = Optional.of(value);

            assertTrue(sut.get() == value);

            // 同じインスタンスの検証には IsSame#sameInstance があるが、
            // 可読性は圧倒的にこちらの方が上。
            // 失敗時のdescriptionを考えればmatcherだが……。
        }

        @Test(expected = NoSuchElementException.class)
        public void get_empty() throws Exception {
            Optional<Object> sut = Optional.empty();

            // emptyなOptionalのgetをそのまま呼ぶと例外になる。
            // 「Optionalを使う=値が入っていない可能性がある」なので、
            // Optional#get をJavaSE7までの文脈で使う理由はないはず。
            sut.get();
        }

        @Test
        public void orElse() throws Exception {
            Optional<Object> sut = Optional.of("hoge");
            // デフォルト値を指定するイメージ。
            // この形式のAPIは Properties#getProperty とか他にもあるので、
            // それほど違和感なく使えると思う。
            Object value = sut.orElse("fuga");
            assertThat(value, is("hoge"));
        }

        @Test
        public void orElse_empty() throws Exception {
            Optional<Object> sut = Optional.empty();
            Object value = sut.orElse("fuga");
            // emptyなのでorElseで指定した値が取得される。
            assertThat(value, is("fuga"));
        }

        @Test
        public void orElseGet() throws Exception {
            Optional<String> sut = Optional.empty();
            // orElseの派生で、lambdaな知識が必要なもの。
            // デフォルト値を作るSupplierを渡す。
            String value = sut.orElseGet(String::new);
            assertThat(value, is(""));

            // Supplierを要求する orElseGet や orElseThrow に対し、
            // メソッド参照できないときにlambdaを書くと、途端に読みづらくなる。
            // lambdaが主体ならいいが、orElseGet や orElseThrow のように、
            // 補助的なときに引数が重くなるのは、焦点がボケるので好ましくない。
        }

        @Test
        public void ifPresent() throws Exception {
            // Optionalを使用したコードの主人公。
            // 今まで if (value != null) ブロックの中に記述していた部分がConsumerとなる。
            AtomicInteger sut = new AtomicInteger(0);
            Optional.of("hoge").ifPresent(value -> sut.getAndAdd(value.length()));

            assertThat(sut.get(), is(4));

            // 単に置き換えるだけだとなんの嬉しさもない。
            // 既存のifブロックとlambdaを比べると、内外の影響力が大きく変わる。（例えば実質的finalなど。）
            // このことがクラスの責務分割などを後押しするのだが、既存に適用しようとすると間違いなく難航する。
        }

        @Test
        public void ifPresent_empty() throws Exception {
            // emptyだと単に実行されない。
            Optional<String> sut = Optional.empty();
            sut.ifPresent(Assert::fail);
        }

        @Test
        public void isPresent() throws Exception {
            // 使ったら負けと言われるメソッドだが、そうでも無い。
            //「単なるnullチェックじゃね？」その通り。
            // しかし「値は要らないが有無は知りたい」場面で get系は使えないのだ。
            Optional<?> sut = Optional.of(new Object());
            assertTrue(sut.isPresent());
        }

        @Test
        public void isPresent_empty() throws Exception {
            // emptyだとfalseになる。特筆することは無い。
            Optional<?> sut = Optional.empty();
            assertFalse(sut.isPresent());
        }

        @Test
        public void orElseThrow() throws Exception {
            Object value = new Object();
            Optional<Object> sut = Optional.of(value);
            // emptyでないので普通にvalueが取得される。
            Object value2 = sut.orElseThrow(IllegalStateException::new);
            assertTrue(value2 == value);
        }

        @Test(expected = Exception.class)
        public void orElseThrow_empty() throws Exception {
            Optional<Object> sut = Optional.empty();
            // emptyだったらSupplierに例外を作らせて投げつける。
            // チェック例外でも良い。
            sut.orElseThrow(Exception::new);

            // 例外をコンストラクタ参照にするとmessageを指定できない。
            // なのでより具体的な例外型を使用したいところ。
            // しかし常にそうできないので、Supplierをlambdaで書きたくなる。
            sut.orElseThrow(() -> new Exception("なにがしかのメッセージ"));
            // lambdaよりも例えばprivateメソッド参照にした方が良い場合もある。
            sut.orElseThrow(this::throwSomeException);
            // 実開発では例外生成のコードは重くなりがちなので検討したい点。
            // 個人的な趣味では例外型を増やす方が好き。
        }

        private Exception throwSomeException() {
            return new Exception("なにがしかのメッセージ");
        }
    }

    /**
     * Optionalに何かして別の（もしくはそのままの）Optionalを返すもの。
     * 基本的にメソッドチェーンでの使用が想定される。
     *
     * @see Optional#map(Function)
     * @see Optional#filter(Predicate)
     * @see Optional#flatMap(Function)
     */
    public static class Operations {

        @Test
        public void map() throws Exception {
            Optional<Object> sut = Optional.of("hoge");
            Optional<Object> mapped = sut.map(a -> new Object());
            // valueを弄って別のOptionalを作る。
            assertThat(mapped, not(isValue("hoge")));

            // 当然だけど、元のOptionalインスタンスは変更されない。
            // これも当然だけど、valueの中身を変えたら影響は受ける。
            // valueを変更するようなことは避けるべきだけど、できてしまうのは事実。
        }

        @Test
        public void map_empty() throws Exception {
            Optional<Object> sut = Optional.empty();
            Optional<Object> mapped = sut.map(a -> new Object());
            // emptyをどんだけmapしてもemptyに変わりはない。
            assertThat(mapped, isEmpty());
        }

        @Test
        public void map_to_empty() throws Exception {
            Optional<Object> sut = Optional.of("hoge");
            Optional<Object> mapped = sut.map(a -> null);
            // 値入ってるのをnullにしたらemptyになる。
            // APIに書かれている仕様化された挙動で現実解ではあるが、好きじゃない。
            // emptyにしたい条件があるならfilterを使って欲しい。
            assertThat(mapped, isEmpty());
        }

        @Test
        public void filter_no_effect() throws Exception {
            Optional<String> sut = Optional.of("");
            // Predicateがtrueになる条件でfilterしてるのでなんの影響もない
            Optional<String> filtered = sut.filter(String::isEmpty);
            assertThat(filtered, isValue(""));
        }

        @Test
        public void filter_to_empty() throws Exception {
            Optional<String> sut = Optional.of("abc");
            // Predicateがfalseなのでemptyになる
            Optional<String> filtered = sut.filter(String::isEmpty);
            assertThat(filtered, isEmpty());
        }

        @Test
        public void flatMap() throws Exception {
            // 雰囲気はmapと同じ。
            Optional<Object> result = Optional.of("hoge")
                    .flatMap(value -> Optional.of(value + "fuga"))
                    .flatMap(Optional::of)
                    .flatMap(value -> Optional.of(value + "piyo"));
            assertThat(result, isValue("hogefugapiyo"));

            // 単にmapに Function<String, Optional<String>> を渡してたとしたら素敵なことになる。
            Optional<Object> result2 = Optional.of("hoge")
                    .map(value -> Optional.of(value + "fuga"))
                    .map(Optional::of)
                    .map(value -> Optional.of(value + "piyo"));
            assertThat(result2.toString(), is("Optional[Optional[Optional[Optional[hogefuga]]piyo]]"));

            // flatMapは「Optionalを返すメソッド」などがある程度作られてから効果を発揮するので、
            // 使い始めのときはそれほど出番は無いと思われる。
            // だからと言って使え無いと評するのは時期尚早だし、知らなかったら途方に暮れることになる。
        }
    }
}
