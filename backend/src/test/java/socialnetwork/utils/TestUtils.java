package socialnetwork.utils;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestUtils {

    @Test
    public void testGetDateForStringPass() throws Exception {
        String dateString = "01-10-1992";
        Date dateObject = Util.getDateForString(dateString);

        GregorianCalendar gregorianCalendar = new GregorianCalendar(1992, 10, 1);
        gregorianCalendar.add(Calendar.MONTH, -1);
        Date dateComparison = gregorianCalendar.getTime();
        assertTrue(DateUtils.isSameDay(dateObject, dateComparison));
    }

    @Test(expected = ParseException.class)
    public void testGetDateForStringError() throws Exception {
        String dateString = "BLUE";
        Util.getDateForString(dateString);
    }

    @Test
    public void testGetStringForDatePass() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(1992, 10, 1);
        gregorianCalendar.add(Calendar.MONTH, -1);
        Date dateObject = gregorianCalendar.getTime();
        String dateString = Util.getStringForDate(dateObject);

        String dateComparison = "01-10-1992";
        assertEquals(dateString, dateComparison);
    }

    @Test
    public void testGetStringForDate2Pass() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(1992, 10, 1);
        gregorianCalendar.add(Calendar.MONTH, -1);
        Date dateObject = gregorianCalendar.getTime();
        String dateString = Util.getStringForDate(dateObject, "dd-M-yyyy");

        String dateComparison = "01-10-1992";
        assertEquals(dateString, dateComparison);
    }
}
