package com.parcel.parcelcosting.factory;

/**
 * Must be inherited where delivery cost to be calculated based on  weight and volume
 */
public interface CostRule {
    Double getDeliveryCost(Double volumeWeight);

}
