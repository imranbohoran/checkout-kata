package com.tib.supermarket.checkout;

import com.tib.supermarket.Item;

class CheckoutItem {
    private final Item item;
    private final int quantity;

    CheckoutItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    Item getItem() {
        return item;
    }

    int getQuantity() {
        return quantity;
    }
}
