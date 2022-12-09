package com.parcel.parcelcosting.factory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementation class of the VolumeRule
 * Calculates the shipping cost based on the volume of the parcel
 */
public class MediumParcelRule implements VolumeRule {

    @Override
    public BigDecimal getDeliveryCost(final BigDecimal volume) {
        return BigDecimal.valueOf(0.04).multiply(volume).setScale(2, RoundingMode.HALF_UP);
    }
}