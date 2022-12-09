package com.parcel.parcelcosting.factory;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class HeavyParcelRule implements WeightRule {
    /**
     * Implementation class of the weight based rule
     * Calculates the parcel delivery cost based on the weight of the parcel
     */
    @Override
    public BigDecimal getDeliveryCost(final BigDecimal weight) {
        return BigDecimal.valueOf(20).multiply(weight).setScale(2, RoundingMode.HALF_UP);
    }
}
