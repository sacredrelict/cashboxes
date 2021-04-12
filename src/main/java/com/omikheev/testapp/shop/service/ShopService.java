package com.omikheev.testapp.shop.service;

import com.omikheev.testapp.shop.dto.CashBox;
import com.omikheev.testapp.shop.dto.Shop;
import com.omikheev.testapp.shop.enums.OperationType;
import com.omikheev.testapp.shop.task.AddBuyerTask;
import com.omikheev.testapp.shop.task.RemoveBuyerTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shop service with fixed thread pool.
 * Fixed pool size depends on the cash boxes count and tasks.
 * In test application we use only 2.
 *
 * @author Oleg Mikheev
 * @since 12.04.2021
 */
public class ShopService {

    private final ExecutorService executorService;
    private final Shop shop;
    private final Lock lock;

    public ShopService(Shop shop) {
        this.shop = shop;
        this.executorService = Executors.newFixedThreadPool(2);
        this.lock = new ReentrantLock();
    }

    /**
     * Get basic shop class.
     *
     * @return {@link Shop}
     */
    public Shop getShop() {
        return shop;
    }

    /**
     * This method run tasks.
     * Lock is important here, because we want to guarantee to all buyers to choose the best queue.
     *
     * @param operationNumber number of operation. 0 - add buyer operation, till 1 to 4 - remove.
     * @return number of cash box.
     */
    public int runTask(int operationNumber) {
        lock.lock();

        OperationType operationType = OperationType.ADD_BUYER;
        if (operationNumber > 0) {
            operationType = OperationType.REMOVE_BUYER;
        }

        int cashBoxNumber = 0;
        switch (operationType) {
            case ADD_BUYER:
                cashBoxNumber = addBuyer();
                System.out.println("Buyer were added to cash box queue #" + cashBoxNumber);
                break;

            case REMOVE_BUYER:
                cashBoxNumber = removeBuyer(operationNumber);
                System.out.println("Buyer were removed from cash box queue #" + cashBoxNumber);
                break;
        }

        lock.unlock();

        return cashBoxNumber;
    }

    /**
     * Add buyer task initialization.
     * IMPORTANT: we use get() just in test purpose. In real application this should be removed.
     *
     * @return int number.
     */
    private int addBuyer() {
        CashBox cashBox = findBestCashBoxForBuyer();
        try {
            executorService.submit(new AddBuyerTask(cashBox)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error while doing AddBuyerTask", e);
        }
        return cashBox.getCashBoxNumber();
    }

    /**
     * Remove buyer task initialization.
     * IMPORTANT: we use get() just in test purpose. In real application this should be removed.
     *
     * @return int number.
     */
    private int removeBuyer(int cashBoxNumber) {
        CashBox cashBox = shop.getCashBoxByNumber(cashBoxNumber);
        try {
            executorService.submit(new RemoveBuyerTask(cashBox)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error while doing RemoveBuyerTask", e);
        }
        return cashBoxNumber;
    }

    /**
     * Find the best cash box for the buyer.
     *
     * @return {@link CashBox}
     */
    private CashBox findBestCashBoxForBuyer() {
        CashBox bestCashBoxForBuyer = null;
        Double hoursToWait = null;

        for (CashBox cashBox : shop.getCashBoxes()) {
            double cashBoxSize = cashBox.getQueue().size();
            if (cashBoxSize == 0) {
                cashBoxSize = 0.01;
            }
            double hoursToWaitTemp = cashBoxSize / cashBox.getProductivityPerHour();
            if (hoursToWait == null || hoursToWait > hoursToWaitTemp) {
                hoursToWait = hoursToWaitTemp;
                bestCashBoxForBuyer = cashBox;
            }
        }

        return bestCashBoxForBuyer;
    }
}
