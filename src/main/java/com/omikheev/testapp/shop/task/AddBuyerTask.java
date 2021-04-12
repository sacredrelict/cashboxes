package com.omikheev.testapp.shop.task;

import com.omikheev.testapp.shop.dto.Buyer;
import com.omikheev.testapp.shop.dto.CashBox;

import java.util.concurrent.Callable;

/**
 * Task for add buyer into the queue.
 *
 * @author Oleg Mikheev
 * @since 12.04.2021
 */
public class AddBuyerTask implements Callable {

    private final CashBox cashBox;

    public AddBuyerTask(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    @Override
    public Object call() throws Exception {
        Buyer buyer = new Buyer();
        cashBox.getQueue().add(buyer);

        return cashBox;
    }
}
