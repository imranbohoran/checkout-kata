package com.tib.supermarket.checkout;

import com.tib.supermarket.checkout.rules.Pricing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.tib.supermarket.checkout.TestData.pricingForItemA;
import static com.tib.supermarket.checkout.TestData.pricingForItemB;
import static com.tib.supermarket.checkout.TestData.pricingForItemC;
import static com.tib.supermarket.checkout.TestData.pricingForItemD;
import static org.assertj.core.api.Assertions.assertThat;

class CsvMultiPriceOfferPricingLoaderTest {

    @Test
    void shouldLoadPricingListFromCsv() throws Exception {

        String csvFilePath = Paths.get(Objects.requireNonNull(CsvMultiPriceOfferPricingLoaderTest.class.getClassLoader()
            .getResource("price-rules-default.csv")).toURI())
            .toFile().getAbsolutePath();

        CsvMultiPriceOfferPricingLoader loader = new CsvMultiPriceOfferPricingLoader();
        List<Pricing> pricings = loader.loadPricingList(csvFilePath);

        assertThat(pricings.size()).isEqualTo(4);

        assertThat(pricings).containsExactlyInAnyOrder(pricingForItemA(), pricingForItemB(), pricingForItemC(), pricingForItemD());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfPriceListFileDoesNotExist() {
        CsvMultiPriceOfferPricingLoader loader = new CsvMultiPriceOfferPricingLoader();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loader.loadPricingList("non-existing-price-rules-file.csv");
        });
    }
}