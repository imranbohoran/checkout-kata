package com.tib.supermarket;

import com.tib.supermarket.checkout.CheckoutTransaction;
import com.tib.supermarket.checkout.CsvMultiPriceOfferPricingLoader;
import com.tib.supermarket.checkout.rules.Pricing;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Till {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter path for pricing rules (fully qualified path):");
        String pricingPath = reader.nextLine();

        CheckoutTransaction checkoutTransaction = new CheckoutTransaction(loadPricingRules(pricingPath));

        promptUser(checkoutTransaction, reader);
    }

    private static void processUserAction(int answer, CheckoutTransaction checkoutTransaction, Scanner reader) {
        switch (answer) {
            case 1:
                scanItem(checkoutTransaction, reader);
                break;
            case 2:
                printTotal(checkoutTransaction, reader);
                break;
            case 3:
                System.exit(0);
            default:
                promptUser(checkoutTransaction, reader);
        }
    }

    private static void scanItem(CheckoutTransaction checkoutTransaction, Scanner reader) {
        System.out.println("Enter item sku: ");
        String sku = reader.next();

        System.out.println("Enter quantity: ");
        int quantity = reader.nextInt();
        checkoutTransaction.addScannedItem(Item.fromSku(sku), quantity);

        promptUser(checkoutTransaction, reader);
    }


    private static void printTotal(CheckoutTransaction checkoutTransaction, Scanner reader) {
        BigDecimal total = checkoutTransaction.getTotal();

        System.out.println("================================");
        System.out.println("=== Checkout total is: "+ total);
        System.out.println("================================");

        promptUser(checkoutTransaction, reader);
    }

    private static void promptUser(CheckoutTransaction checkoutTransaction, Scanner reader) {
        System.out.println("What do you want to do?");
        System.out.println(">>> 1. Scan an item");
        System.out.println(">>> 2. Get total");
        System.out.println(">>> 3. Exit checkout");
        int answer = reader.nextInt();

        processUserAction(answer, checkoutTransaction, reader);
    }

    private static List<Pricing> loadPricingRules(String rulesPath) {
        List<Pricing> pricingList = new CsvMultiPriceOfferPricingLoader().loadPricingList(rulesPath);
        System.out.println("Loaded pricing rules: " + pricingList);

        return pricingList;
    }
}
