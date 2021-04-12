package com.omikheev.testapp.shop.task;

import com.omikheev.testapp.shop.dto.CashBox;

import java.util.concurrent.Callable;

/**
 * Task for remove buyer from the queue.
 *
 * @author Oleg Mikheev
 * @since 12.04.2021
 */
public class RemoveBuyerTask implements Callable {

    private final CashBox cashBox;

    public RemoveBuyerTask(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    @Override
    public Object call() throws Exception {
        if (!cashBox.getQueue().isEmpty()) {
            cashBox.getQueue().remove();
        }

        return cashBox;
    }
}
