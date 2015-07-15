package jsr310;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertFalse;

/**
 * @author irof
 */
public class Test {
    @org.junit.Test
    public void testCompareDate() throws Exception {
        Date date1 = new Date();
        Date date2 = new Date();

        assertFalse(date1.before(date2));
        assertFalse(date2.before(date1));
        assertFalse(date1.after(date2));
        assertFalse(date2.after(date1));
    }

    @org.junit.Test
    public void testCompareLocalDateTime() throws Exception {
        LocalDateTime localDate1 = LocalDateTime.of(2015, 7, 11, 12, 34);
        LocalDateTime localDate2 = LocalDateTime.of(2015, 7, 11, 12, 34);

        assertFalse(localDate1.isBefore(localDate2));
        assertFalse(localDate2.isBefore(localDate1));
        assertFalse(localDate1.isAfter(localDate2));
        assertFalse(localDate2.isAfter(localDate1));
    }
}
