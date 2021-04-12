package com.omikheev.testapp.shop;

import com.omikheev.testapp.shop.dto.CashBox;
import com.omikheev.testapp.shop.dto.Shop;
import com.omikheev.testapp.shop.service.ShopService;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


/**
 * Shop application main class.
 *
 * @author Oleg Mikheev
 * @since 12.04.2021
 */
public class ShopApp {

    private static final int CASH_BOXES_COUNT = 4;
    private static final List<Integer> CASH_BOX_PRODUCTIVITY_LIST = new LinkedList<>(Arrays.asList(10, 13, 15, 17));

    private static ShopService shopService;

    /**
     * Application run method.
     *
     * @param args possible arguments.
     */
    public static void main(String[] args) {
        System.out.println("Start Shop application");

        Shop shop = new Shop(CASH_BOXES_COUNT, CASH_BOX_PRODUCTIVITY_LIST);
        shopService = new ShopService(shop);

        runInputListener();
    }

    /**
     * Initialize and read input.
     * public for test purpose.
     *
     * @return int number.
     */
    public static int getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    /**
     * Input listener.
     *
     * Possible values:
     * 0 - add buyer to the best queue.
     * 1 - remove buyer from queue 1.
     * 2 - remove buyer from queue 2.
     * 3 - remove buyer from queue 3.
     * 4 - remove buyer from queue 4.
     */
    private static void runInputListener() {
        while (true) {
            try {
                System.out.println("Enter the cash box number from 1 to " + CASH_BOXES_COUNT);
                System.out.println("Or input 0 just for add a buyer, without removing someone from the queues.");
                int operationNumber = getInput();
                if (operationNumber < 0 || operationNumber > CASH_BOXES_COUNT) {
                    throw new InputMismatchException();
                }

                int cashBoxNumber = shopService.runTask(operationNumber);
                System.out.print(operationNumber + " -> " + cashBoxNumber);

                //For DEBUG purposes
                System.out.println();
                for (CashBox cashBox : shopService.getShop().getCashBoxes()) {
                    System.out.println(cashBox.toString());
                }
                System.out.println();

            } catch (InputMismatchException e) {
                System.out.println("Invalid value!");
                break;
            }
        }
    }
}
