package com.parcel.parcelcosting.factory;

import java.math.BigDecimal;

/**
 * WeightRule interface should be implemented by any class whose instances are intended for calculating the cost based on the weight of the parcel
 */
public interface WeightRule {
    BigDecimal getDeliveryCost(final BigDecimal weight);
}
