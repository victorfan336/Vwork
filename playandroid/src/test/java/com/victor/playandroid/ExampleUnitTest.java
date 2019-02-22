package com.victor.playandroid;

import android.util.Log;

import com.victor.baselib.utils.XLog;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition() {
        assertEquals(4, 2 + 2);
        Double rotate =  Math.atan2(1, 1);
        System.out.print("rotate = " + 180 * rotate / Math.PI);
    }
}