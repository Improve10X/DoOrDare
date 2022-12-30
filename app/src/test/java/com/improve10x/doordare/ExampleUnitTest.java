package com.improve10x.doordare;

import org.junit.Test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dateTest() {
        long timestamp = 1672486200000L;//System.currentTimeMillis();
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm:ss aa");
        String dateStr = format.format(date);
        System.out.println(timestamp);
        System.out.println(dateStr);
    }
}