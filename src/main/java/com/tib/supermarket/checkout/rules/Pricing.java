package com.tib.supermarket.checkout.rules;

import com.tib.supermarket.Item;

import java.math.BigDecimal;
import java.util.Objects;

public class Pricing {

    private Item item;
    private BigDecimal price;
    private MultiPriceOffer specialPrice;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultiPriceOffer getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(MultiPriceOffer specialPrice) {
        this.specialPrice = specialPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pricing pricing = (Pricing) o;
        return Objects.equals(item, pricing.item) &&
            Objects.equals(price, pricing.price) &&
            Objects.equals(specialPrice, pricing.specialPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, price, specialPrice);
    }

    @Override
    public String toString() {
        return "Pricing{" +
            "item=" + item +
            ", price=" + price +
            ", specialPrice=" + specialPrice +
            '}';
    }
}
