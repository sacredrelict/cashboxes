package com.omikheev.testapp.shop.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Shop representation object.
 *
 * @author Oleg Mikheev
 * @since 12.04.2021
 */
public final class Shop {

    private final List<CashBox> cashBoxes;
    private final List<Integer> cashBoxProductivityList;

    public Shop(int cashBoxesCount, List<Integer> cashBoxProductivityList) {
        this.cashBoxes = new LinkedList<>();
        this.cashBoxProductivityList = cashBoxProductivityList;

        initializeCashBoxes(cashBoxesCount);
    }

    /**
     * Get the list of all cash boxes.
     *
     * @return list of {@link CashBox}
     */
    public List<CashBox> getCashBoxes() {
        return cashBoxes;
    }

    /**
     * Util method to find Cash Box by it's number.
     *
     * @param cashBoxNumber cash box number
     * @return {@link CashBox}
     */
    public CashBox getCashBoxByNumber(int cashBoxNumber) {
        for (CashBox cashBox : cashBoxes) {
            if (cashBox.getCashBoxNumber() == cashBoxNumber) {
                return cashBox;
            }
        }
        return null;
    }

    private void initializeCashBoxes(int cashBoxesCount) {
        int productivityPerHour = 10;
        for (int i = 1; i <= cashBoxesCount; i++) {
            if (i <= cashBoxProductivityList.size()) {
                productivityPerHour = cashBoxProductivityList.get(i-1);
            }
            cashBoxes.add(new CashBox(i, productivityPerHour));
        }
    }
}
