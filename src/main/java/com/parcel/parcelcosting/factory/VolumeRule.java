package com.parcel.parcelcosting.factory;

import java.math.BigDecimal;

/**
 * VolumeRule interface should be implemented by any class whose instances are intended for calculating the cost based on the volume of the parcel
 */
public interface VolumeRule {

    BigDecimal getDeliveryCost(final BigDecimal volume);

}
