package com.tib.supermarket;

import java.util.Objects;

public class Item {

    private final String sku;

    public Item(String sku) {this.sku = sku;}

    public static Item fromSku(String sku) {
        return new Item(sku);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(sku, item.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }
}
