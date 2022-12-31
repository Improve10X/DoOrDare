package com.improve10x.doordare;

import static org.junit.Assert.assertEquals;


import com.improve10x.doordare.utils.DateUtils;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateUtilsTest {

    @Test
    public void getFormattedTimeWith1Hour() {
        String expectedOutput = "01:00:00";
        String actualOutput = DateUtils.getFormattedTime(60 * 60 * 1000);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getFormattedTimeWith5Minutes() {
        String expectedOutput = "05:00";
        String actualOutput = DateUtils.getFormattedTime(5 * 60 * 1000);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getTimeLeftTextWith5Minutes() {
        String expectedOutput = "5 minutes ";
        String actualOutput = DateUtils.getTimeLeftText(5 * 60 * 1000);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getAdvancedTimeLeftTextWith5Minutes20Seconds() {
        String expectedOutput = "5 minutes 20 seconds ";
        String actualOutput = DateUtils.getAdvancedTimeLeftText(5 * 60 * 1000 + 20 * 1000);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getAdvancedTimeLeftTextWith10Minutes20Seconds() {
        String expectedOutput = "10 minutes ";
        String actualOutput = DateUtils.getAdvancedTimeLeftText(10 * 60 * 1000 + 20 * 1000);
        assertEquals(expectedOutput, actualOutput);
    }

}