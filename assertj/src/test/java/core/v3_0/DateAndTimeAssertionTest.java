package core.v3_0;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date and Time APIの検証メソッド。
 * 特にびっくりするような使い方はないので、使う時にメソッド眺めて適当に使えばよさそう。
 *
 * @author irof
 * @version 3.0.0
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-3.0.0-new-date-assertions"> Java 8 Date/Time assertions.</a>
 */
public class DateAndTimeAssertionTest {

    @Test
    public void LocalDateTimeの検証() throws Exception {
        LocalDateTime sut = LocalDateTime.of(2001, Month.JANUARY, 2, 3, 4, 5);

        LocalDateTime 時が違うやつ = LocalDateTime.of(2001, Month.JANUARY, 2, 9, 4, 6);
        LocalDateTime 分が違うやつ = LocalDateTime.of(2001, Month.JANUARY, 2, 3, 9, 6);
        LocalDateTime 秒が違うやつ = LocalDateTime.of(2001, Month.JANUARY, 2, 3, 4, 9);
        LocalDateTime ナノが違うやつ = LocalDateTime.of(2001, Month.JANUARY, 2, 3, 4, 5, 9);

        assertThat(sut)
                // `parse`できる書式ならそのまま書けばOK
                .isEqualTo("2001-01-02T03:04:05")
                .isBefore("2099-12-31T00:00:00")
                .isAfter("1999-12-31T00:00:00")
                // `isEqualToIgnoringXxxx` はtrimして比較してたのを簡易化してくれる感じ。
                // 実装は年月日時分秒をそれぞれ比較するメソッドを順番に呼んるので、
                // `isEqualToIgnoringDays` とかがないのが不自然な感じがするけど、
                // `LocalDateTime`の検証でその需要はないんだろう。
                .isEqualToIgnoringHours(時が違うやつ)
                .isEqualToIgnoringMinutes(分が違うやつ)
                .isEqualToIgnoringSeconds(秒が違うやつ)
                .isEqualToIgnoringNanos(ナノが違うやつ);
    }

    @Test
    public void LocalTimeの検証() throws Exception {
        // 他の型でも特筆することはなさそう
        // IgnoringがSecondsとNanosしかないくらい。

        LocalTime sut = LocalTime.parse("01:02:03");

        assertThat(sut)
                .isEqualTo("01:02:03")
                .isEqualToIgnoringSeconds(LocalTime.parse("01:02:09"))
                .isEqualToIgnoringNanos(LocalTime.parse("01:02:03.456"));
    }

    @Test
    public void 今日の検証() throws Exception {
        // v3.3.0で追加された
        LocalDate now = LocalDate.now();

        assertThat(now).isToday();
    }
}
