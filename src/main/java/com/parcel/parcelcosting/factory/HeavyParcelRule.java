package com.parcel.parcelcosting.factory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementation class of the WeightRule
 * Calculates the shipping cost based on the weight of the parcel
 */
public class HeavyParcelRule implements WeightRule {
    @Override
    public BigDecimal getDeliveryCost(final BigDecimal weight) {
        return BigDecimal.valueOf(20).multiply(weight).setScale(2, RoundingMode.HALF_UP);
    }
}
