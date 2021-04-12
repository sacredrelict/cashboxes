package com.omikheev.testapp.shop.service;

import com.omikheev.testapp.shop.dto.Shop;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link ShopService} class.
 *
 * @author Oleg Mikheev
 * @since 12.04.2021
 */
public class ShopServiceTest {

    private static final int CASH_BOXES_COUNT = 4;
    private static final List<Integer> CASH_BOX_PRODUCTIVITY_LIST = new LinkedList<>(Arrays.asList(10, 13, 15, 17));

    private ShopService shopService;

    @Before
    public void initialize() {
        Shop shop = new Shop(CASH_BOXES_COUNT, CASH_BOX_PRODUCTIVITY_LIST);
        shopService = new ShopService(shop);
    }

    @Test
    public void testRunTask_addBuyer() {
        int cashBoxNumber = shopService.runTask(0);
        assertEquals(4, cashBoxNumber);
    }

    @Test
    public void testRunTask_removeBuyer() {
        shopService.runTask(0);
        assertEquals(1, shopService.getShop().getCashBoxes().get(3).getQueue().size());

        int cashBoxNumber = shopService.runTask(4);
        assertEquals(4, cashBoxNumber);
        assertEquals(0, shopService.getShop().getCashBoxes().get(3).getQueue().size());
    }

    @Test
    public void testRunTask_addFourBuyers() {
        for (int i = 0; i < 4; i++) {
            shopService.runTask(0);
        }

        assertEquals(1, shopService.getShop().getCashBoxes().get(0).getQueue().size());
        assertEquals(1, shopService.getShop().getCashBoxes().get(1).getQueue().size());
        assertEquals(1, shopService.getShop().getCashBoxes().get(2).getQueue().size());
        assertEquals(1, shopService.getShop().getCashBoxes().get(3).getQueue().size());
    }

    @Test
    public void testRunTask_add20Buyers() {
        for (int i = 0; i < 20; i++) {
            shopService.runTask(0);
        }

        assertEquals(4, shopService.getShop().getCashBoxes().get(0).getQueue().size());
        assertEquals(5, shopService.getShop().getCashBoxes().get(1).getQueue().size());
        assertEquals(5, shopService.getShop().getCashBoxes().get(2).getQueue().size());
        assertEquals(6, shopService.getShop().getCashBoxes().get(3).getQueue().size());
    }

    @Test(expected = RuntimeException.class)
    public void testRunTask_QueueIsFull() {
        for (int i = 0; i < 100; i++) {
            shopService.runTask(0);
        }
    }

    @Test
    public void testRunTask_add100Buyers() {
        for (int i = 0; i < 65; i++) {
            shopService.runTask(0);
        }

        assertEquals(12, shopService.getShop().getCashBoxes().get(0).getQueue().size());
        assertEquals(15, shopService.getShop().getCashBoxes().get(1).getQueue().size());
        assertEquals(18, shopService.getShop().getCashBoxes().get(2).getQueue().size());
        assertEquals(20, shopService.getShop().getCashBoxes().get(3).getQueue().size());

        // remove 20 buyers from queue 4
        for (int i = 0; i < 20; i++) {
            shopService.runTask(4);
        }
        // remove 18 buyers from queue 3
        for (int i = 0; i < 18; i++) {
            shopService.runTask(3);
        }
        // add more 35 buyers
        for (int i = 0; i < 35; i++) {
            shopService.runTask(0);
        }

        assertEquals(12, shopService.getShop().getCashBoxes().get(0).getQueue().size());
        assertEquals(15, shopService.getShop().getCashBoxes().get(1).getQueue().size());
        assertEquals(16, shopService.getShop().getCashBoxes().get(2).getQueue().size());
        assertEquals(19, shopService.getShop().getCashBoxes().get(3).getQueue().size());
    }
}
