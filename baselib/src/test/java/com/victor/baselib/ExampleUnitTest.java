package com.victor.baselib;

import android.util.Log;

import com.victor.baselib.utils.ZipUtils;

import org.junit.Test;

import static org.junit.Assert.*;

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
    public void unZipFolder() {
        try {
            ZipUtils.unZipFolder("E:\\NotificationListenerService-Example-master.zip", "E:\\");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("victor", e.getMessage());
        }
    }
}