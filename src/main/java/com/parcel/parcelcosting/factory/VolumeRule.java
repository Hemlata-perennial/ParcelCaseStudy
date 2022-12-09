package com.parcel.parcelcosting.factory;

import java.math.BigDecimal;

/**
 * Must be inherited where delivery cost to be calculated based on volume rule
 */
public interface VolumeRule {
    BigDecimal getDeliveryCost(final BigDecimal volume);

}
