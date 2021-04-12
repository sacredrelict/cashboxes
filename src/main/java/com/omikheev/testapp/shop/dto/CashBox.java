package com.omikheev.testapp.shop.dto;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * CashBox representation object.
 *
 * @author Oleg Mikheev
 * @since 12.04.2021
 */
public final class CashBox {

    private static final int MAX_CAPACITY = 20;

    private final Queue<Buyer> queue;
    private final int cashBoxNumber;
    private final int productivityPerHour;

    public CashBox(int cashBoxNumber, int productivityPerHour) {
        this.queue = new LinkedBlockingQueue<>(MAX_CAPACITY);
        this.cashBoxNumber = cashBoxNumber;
        this.productivityPerHour = productivityPerHour;
    }

    /**
     * Returns queue of buyers.
     *
     * @return queue of {@link Buyer}
     */
    public Queue<Buyer> getQueue() {
        return queue;
    }

    /**
     * Returns cash box number.
     *
     * @return int number
     */
    public int getCashBoxNumber() {
        return cashBoxNumber;
    }

    /**
     * Returns productivity per hour.
     *
     * @return int number
     */
    public int getProductivityPerHour() {
        return productivityPerHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashBox cashBox = (CashBox) o;
        return cashBoxNumber == cashBox.cashBoxNumber &&
                productivityPerHour == cashBox.productivityPerHour &&
                Objects.equals(queue, cashBox.queue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queue, cashBoxNumber, productivityPerHour);
    }

    @Override
    public String toString() {
        return "CashBox{" +
                "queue size=" + queue.size() +
                ", cashBoxNumber=" + cashBoxNumber +
                '}';
    }
}
