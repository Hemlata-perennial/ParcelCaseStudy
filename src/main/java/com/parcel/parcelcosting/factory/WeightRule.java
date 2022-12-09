package com.parcel.parcelcosting.factory;

import java.math.BigDecimal;
/**
 * Must be inherited where delivery cost to be calculated based on weigth rule
 */
public interface WeightRule {
    BigDecimal getDeliveryCost(final BigDecimal weight);
}
