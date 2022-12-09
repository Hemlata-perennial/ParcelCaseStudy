package com.parcel.parcelcosting.factory;

public class ParcelRules {

    /**
     * Implementation class of the volume based rule
     * Calculates the parcel delivery cost based on the volume of the parcel
     */
    public static class RejectParcel implements CostRule{
        @Override
        public Double getDeliveryCost(Double weight) {
            return 0.0;
        }
    }

    public static class HeavyParcel implements CostRule{
        @Override
        public Double getDeliveryCost(Double weight) {
            return 20 * weight;
        }
        //code
    }
    public static class SmallParcel implements CostRule{
        @Override
        public Double getDeliveryCost(Double volume) {
            return 0.03 * volume;
        }
    }

    public static class MediumParcel implements CostRule{
        @Override
        public Double getDeliveryCost(Double volume) {
            return 0.04 * volume;
        }
    }

    public static class LargeParcel implements CostRule{
        @Override
        public Double getDeliveryCost(Double volume) {
            return 0.04 * volume;
        }
    }
}
