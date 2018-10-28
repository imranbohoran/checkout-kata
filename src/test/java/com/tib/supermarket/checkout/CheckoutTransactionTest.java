package com.tib.supermarket.checkout;

import com.tib.supermarket.Item;
import com.tib.supermarket.checkout.rules.MultiPriceOffer;
import com.tib.supermarket.checkout.rules.Pricing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.tib.supermarket.checkout.CheckoutTransactionTest.TestScanItem.testScanItem;
import static com.tib.supermarket.checkout.TestData.pricingForItemA;
import static com.tib.supermarket.checkout.TestData.pricingForItemB;
import static com.tib.supermarket.checkout.TestData.pricingForItemC;
import static com.tib.supermarket.checkout.TestData.pricingForItemD;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class CheckoutTransactionTest {

    @ParameterizedTest(name = "{index} => pricingList={0}, testScanItems={1}, expectedTotal={2}")
    @MethodSource("checkoutDataProvider")
    void shouldCalculateTotalWhenItemScanned(List<Pricing> pricingList, List<TestScanItem> testScanItems, BigDecimal expectedTotal) {

        CheckoutTransaction checkoutTransaction = new CheckoutTransaction(pricingList);

        testScanItems.forEach(testScanItem -> checkoutTransaction.addScannedItem(testScanItem.item, testScanItem.quantity));

        assertThat(checkoutTransaction.getTotal()).isEqualByComparingTo(expectedTotal);
    }

    @Test
    void scanningItemWithoutPricingInformationShouldThrowIllegalState() {
        CheckoutTransaction checkoutTransaction = new CheckoutTransaction(Collections.emptyList());

        Assertions.assertThrows(IllegalStateException.class,
            () -> checkoutTransaction.addScannedItem(Item.fromSku("A"), 10));
    }

    @Test
    void scanningItemsWithZeroQuantityShouldThrowIllegalArgumentException() {
        CheckoutTransaction checkoutTransaction = new CheckoutTransaction(asList(pricingForItemA(), pricingForItemB()));

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> checkoutTransaction.addScannedItem(Item.fromSku("A"), 0));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenMultiPriceOfferHasZeroQuantity() {
        Pricing pricing = pricingForItemC();
        pricing.setSpecialPrice(new MultiPriceOffer(0, BigDecimal.valueOf(10)));

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new CheckoutTransaction(asList(pricingForItemA(), pricing)));
    }

    private static Stream<Arguments> checkoutDataProvider() {
        return Stream.of(
            Arguments.of(
                getPricingList(),
                singletonList(testScanItem(Item.fromSku("C"), 10)),
                BigDecimal.valueOf(200)),
            Arguments.of(
                getPricingList(),
                asList(
                    testScanItem(Item.fromSku("D"), 10),
                    testScanItem(Item.fromSku("D"), 5)
                ),
                BigDecimal.valueOf(225)),
            Arguments.of(
                getPricingList(),
                asList(
                    testScanItem(Item.fromSku("B"), 1),
                    testScanItem(Item.fromSku("A"), 1),
                    testScanItem(Item.fromSku("B"), 1)
                ),
                BigDecimal.valueOf(95)),
            Arguments.of(
                getPricingList(),
                asList(
                    testScanItem(Item.fromSku("A"), 1),
                    testScanItem(Item.fromSku("B"), 1),
                    testScanItem(Item.fromSku("A"), 1),
                    testScanItem(Item.fromSku("B"), 1),
                    testScanItem(Item.fromSku("A"), 1),
                    testScanItem(Item.fromSku("B"), 1)
                ),
                BigDecimal.valueOf(205))
        );
    }

    static class TestScanItem {
        final Item item;
        final int quantity;

        private TestScanItem(Item item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        static TestScanItem testScanItem(Item item, int quantity) {
            return new TestScanItem(item, quantity);
        }

        @Override
        public String toString() {
            return "TestScanItem{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}';
        }
    }

    private static List<Pricing> getPricingList() {
        return asList(pricingForItemA(), pricingForItemB(), pricingForItemC(), pricingForItemD());
    }

}