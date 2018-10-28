package com.tib.supermarket.checkout;

import com.tib.supermarket.Item;
import com.tib.supermarket.checkout.rules.Pricing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class CheckoutTransaction {

    private final List<Pricing> pricingRules;

    private Map<Item, Pricing> itemPricingMap;

    private List<CheckoutItem> items;

    /**
     * Initialises a checkout transaction that enables adding items that are scanned
     * and generate the total of the checkout transaction.
     * Expects a valid pricing rules set to be supplied. Quantites provided in any
     * multi-priced items should be greater than 0.
     *
     * @param pricingRules The pricing rules to initialise the checkout transaction
     */
    public CheckoutTransaction(List<Pricing> pricingRules) {
        validatePricingRules(pricingRules);
        this.pricingRules = pricingRules;

        itemPricingMap = new HashMap<>();
        items = new ArrayList<>();
    }

    private void validatePricingRules(List<Pricing> pricingRules) {
        pricingRules.forEach(pricing -> {
            if (pricing.getSpecialPrice() != null) {
                if (pricing.getSpecialPrice().getQuantity() < 1) {
                    throw new IllegalArgumentException("Quantity for multi price invalid. " + pricing);
                }
            }
        });
    }

    /**
     * Adds a scanned item to the checkout transaction.
     * Expects an Item existing in the pricing list and a quantity greater than 0.
     *
     * @param item The item that exists in the pricing list used to setup the checkout transaction
     * @param quantity A quantity greater than 0 to be added to the checkout transaction
     */
    public void addScannedItem(Item item, int quantity) {

        if (quantity < 1) {
            throw new IllegalArgumentException("Invalid quantity provided. Has to be greater than equal to 1");
        }

        Pricing itemPricing = pricingRules
            .stream()
            .filter(rule -> rule.getItem().equals(item))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Pricing information for item with the given SKU not found"));

        items.add(new CheckoutItem(item, quantity));
        itemPricingMap.put(item, itemPricing);
    }

    /**
     * Calculates the total of this checkout transaction based on the items, quantities added
     * and the pricing rules supplied when the the transaction was initialised.
     *
     * @return The total calculated for the items in this checkout transaction.
     */
    public BigDecimal getTotal() {
        Map<Item, List<CheckoutItem>> scannedItemsGroupedByItem =
            items.stream().collect(groupingBy(CheckoutItem::getItem));

        return scannedItemsGroupedByItem
            .entrySet()
            .stream()
            .map(uniqueItem -> {
                int totalQtyForItem = uniqueItem.getValue().stream().mapToInt(CheckoutItem::getQuantity).sum();
                return new CheckoutItem(uniqueItem.getKey(), totalQtyForItem);
            })
            .map(this::calculatePricing)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePricing(CheckoutItem checkoutItem) {

        Pricing pricing = itemPricingMap.get(checkoutItem.getItem());

        if (pricing.getSpecialPrice() != null) {
            int offerItemGroupCount = checkoutItem.getQuantity() / pricing.getSpecialPrice().getQuantity();
            int nonOfferItemCount = checkoutItem.getQuantity() % pricing.getSpecialPrice().getQuantity();

            BigDecimal offerPrice = pricing.getSpecialPrice().getOfferPrice().multiply(BigDecimal.valueOf(offerItemGroupCount));
            BigDecimal nonOfferPrice = pricing.getPrice().multiply(BigDecimal.valueOf(nonOfferItemCount));

            return offerPrice.add(nonOfferPrice);
        }

        return pricing.getPrice().multiply(BigDecimal.valueOf(checkoutItem.getQuantity()));
    }
}
