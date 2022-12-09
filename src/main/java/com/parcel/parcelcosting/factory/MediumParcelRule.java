package com.parcel.parcelcosting.factory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MediumParcelRule implements VolumeRule {

    /**
     * Implementation class of the volume based rule
     * Calculates the parcel delivery cost based on the volume of the parcel
     */
    @Override
    public BigDecimal getDeliveryCost(final BigDecimal volume) {
        return BigDecimal.valueOf(0.04).multiply(volume).setScale(2, RoundingMode.HALF_UP);
    }
}
