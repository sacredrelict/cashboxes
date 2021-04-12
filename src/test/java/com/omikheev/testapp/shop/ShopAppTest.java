package com.omikheev.testapp.shop;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link ShopApp} class.
 *
 * @author Oleg Mikheev
 * @since 12.04.2021
 */
public class ShopAppTest {

    @Test
    public void testMain() {
        ShopApp inputOutput = new ShopApp();

        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals(0, inputOutput.getInput());
    }
}
