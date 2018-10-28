package com.tib.supermarket.checkout.rules;

import java.math.BigDecimal;
import java.util.Objects;

public class MultiPriceOffer {
    private final int quantity;
    private final BigDecimal offerPrice;

    public MultiPriceOffer(int quantity, BigDecimal offerPrice) {
        this.quantity = quantity;
        this.offerPrice = offerPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    @Override
    public String toString() {
        return "MultiPriceOffer{" +
            "quantity=" + quantity +
            ", offerPrice=" + offerPrice +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MultiPriceOffer that = (MultiPriceOffer) o;
        return quantity == that.quantity &&
            Objects.equals(offerPrice, that.offerPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, offerPrice);
    }
}
