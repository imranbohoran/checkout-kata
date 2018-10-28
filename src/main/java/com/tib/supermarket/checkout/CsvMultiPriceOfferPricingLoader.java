package com.tib.supermarket.checkout;

import com.tib.supermarket.Item;
import com.tib.supermarket.checkout.rules.MultiPriceOffer;
import com.tib.supermarket.checkout.rules.Pricing;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvMultiPriceOfferPricingLoader {

    /**
     * Loads the pricing rules from a csv that is predefined in its format.
     * The expected format is values with the following header and comma separated.
     * <code>header_item_sku,header_unit_price,header_offer_quantity,header_offer_price</code>
     *
     * @param csvPath The path of the csv file
     * @return A list of Pricing objects that represent the pricing rules in the csv
     */
    public List<Pricing> loadPricingList(String csvPath) {

        File csvFile = new File(csvPath);
        List<Pricing> pricingList = new ArrayList<>();

        try (Scanner scanner = new Scanner(csvFile)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if(line.startsWith("header_")) {
                    continue;
                }
                String content[] = line.split(",");
                Pricing pricing = new Pricing();
                pricing.setItem(Item.fromSku(content[0]));
                pricing.setPrice(new BigDecimal(content[1]));
                if (content.length > 2) {
                    pricing.setSpecialPrice(new MultiPriceOffer(Integer.valueOf(content[2]), new BigDecimal(content[3])));
                }
                pricingList.add(pricing);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Provided file with path : "+ csvPath + " not found");
        }
        return pricingList;
    }
}
