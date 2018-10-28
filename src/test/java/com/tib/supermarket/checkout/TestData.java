package com.tib.supermarket.checkout;

import com.tib.supermarket.Item;
import com.tib.supermarket.checkout.rules.MultiPriceOffer;
import com.tib.supermarket.checkout.rules.Pricing;

import java.math.BigDecimal;

public class TestData {

    static Pricing pricingForItemA() {
        Pricing pricingRuleA = new Pricing();
        Item itemA = Item.fromSku("A");
        pricingRuleA.setItem(itemA);
        pricingRuleA.setPrice(BigDecimal.valueOf(50));
        pricingRuleA.setSpecialPrice(new MultiPriceOffer(3, BigDecimal.valueOf(130)));

        return pricingRuleA;
    }

    static Pricing pricingForItemB() {
        Pricing pricingRulesB = new Pricing();
        Item itemB = Item.fromSku("B");
        pricingRulesB.setItem(itemB);
        pricingRulesB.setPrice(BigDecimal.valueOf(30));
        pricingRulesB.setSpecialPrice(new MultiPriceOffer(2, BigDecimal.valueOf(45)));

        return pricingRulesB;
    }

    static Pricing pricingForItemC() {
        Pricing pricingRuleC = new Pricing();
        Item itemC = Item.fromSku("C");
        pricingRuleC.setItem(itemC);
        pricingRuleC.setPrice(BigDecimal.valueOf(20));

        return pricingRuleC;
    }

    static Pricing pricingForItemD() {
        Pricing pricingRuleD = new Pricing();
        Item itemD = Item.fromSku("D");
        pricingRuleD.setItem(itemD);
        pricingRuleD.setPrice(BigDecimal.valueOf(15));

        return pricingRuleD;
    }

}
