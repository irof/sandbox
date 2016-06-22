package misc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author irof
 */
public class CalendarBuilderTest {

    @Test
    public void 昔ながらのCalendarの作り方_getInstance() throws Exception {
        Calendar calendar = Calendar.getInstance();

        assertThat(calendar, is(notNullValue()));
    }

    @Test
    public void 昔ながらのCalendarの作り方_GregorianCalendarのコンストラクタ() throws Exception {
        Calendar calendar = new GregorianCalendar(1970, 0, 1);

        assertThat(calendar, is(notNullValue()));
    }

    @Test
    public void Builderを使用したCalendarインスタンスの作り方() throws Exception {
        Calendar calendar = new Calendar.Builder().build();

        assertThat(calendar, is(notNullValue()));

        // どのフィールドも何も設定されていないみたいなインスタンスができる。
        // TimeZoneとLocale(由来で設定されるfirstDayOfWeek,minimalDaysInFirstWeek)は、
        // それぞれデフォルト値が設定されることになる。
        //System.out.println(calendar);
        assertThat(calendar.getTimeZone(), is(TimeZone.getDefault()));
    }

    @Test
    public void DateやEpochを設定したCalendarを作る_これから() throws Exception {
        // setInstant メソッドのオーバーロードになっている。
        // 引数型が違うのでオーバーロードは適切な使い方だと思います。
        // Instant は今までの Calendar にはなかった言葉で、
        // 「CalendarのsetTimeとかの代わりにsetInstantを使う」という変換が必要になってる。
        Calendar cal1 = new Calendar.Builder().setInstant(12345).build();
        Calendar cal2 = new Calendar.Builder().setInstant(new Date(12345)).build();

        assertTrue(cal1.equals(cal2));
    }

    @Test
    public void DateやEpochを設定したCalendarを作る_いままで() throws Exception {
        // 従来は"一度作ったインスタンスに設定する"形
        // 「Timeってなんやの？ああ、Date#getTime由来ね……」となる不適切なメソッド名であり、
        // なぜsetDateにしなかったんだろうと思ってたりする。
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date(67890));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(67890);

        assertTrue(cal1.equals(cal2));
    }

    @Test
    public void Builder使っても同じ時間ならちゃんと同値になる() throws Exception {
        Calendar oldCalendar = Calendar.getInstance();
        oldCalendar.setTimeInMillis(12345);

        Calendar newCalendar = new Calendar.Builder().setInstant(12345).build();

        assertTrue(oldCalendar.equals(newCalendar));
    }

    @Test(expected = IllegalStateException.class)
    public void 同時に設定すると例外_setInstant_setDate() throws Exception {
        new Calendar.Builder()
                .setInstant(new Date())
                .setDate(2015, 5, 10) // throw ISE
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void 同時に設定すると例外_setDate_setInstant() throws Exception {
        new Calendar.Builder()
                .setDate(2015, 5, 10)
                .setInstant(new Date()) // throw ISE
                .build();
    }
}
