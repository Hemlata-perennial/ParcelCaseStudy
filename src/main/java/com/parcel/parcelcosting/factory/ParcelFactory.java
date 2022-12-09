package com.parcel.parcelcosting.factory;

import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.exception.UnsupportedRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;


/**
 * The purpose of the class is to define rules based volume and weight using design pattern
 */
public class ParcelFactory {
    static Logger logger = LoggerFactory.getLogger(ParcelFactory.class);


    public ParcelFactory() {
    }

    public static Double getDeliveryCost(final Parcel parcel) {

        Double weight;
        //get volume
        Double volume = Parcel.getVolume(parcel);

        //set weight
        if (parcel.getWeight() != null) {
            weight = parcel.getWeight();
        } else {
            weight = 0.0;
        }

        /**
         * @param costRules immutable map to store rules based on weight and volume
         * **/
        Map<Predicate<Parcel>, CostRule> costRules;
        costRules = new LinkedHashMap<>();
        costRules.put(parcelL -> Double.valueOf(parcel.getWeight()).compareTo(50.0) > 0,new ParcelRules.RejectParcel());
        costRules.put(parcelL -> Double.valueOf(parcel.getWeight()).compareTo(10.0) > 0,new ParcelRules.HeavyParcel());
        costRules.put(parcelL -> Parcel.getVolume(parcel).compareTo(1500.0) < 0, new ParcelRules.SmallParcel());
        costRules.put(parcelL -> Parcel.getVolume(parcel).compareTo(2500.0) < 0, new ParcelRules.MediumParcel());
        costRules.put(parcelL -> Parcel.getVolume(parcel).compareTo(2500.0) >= 0, new ParcelRules.LargeParcel());


        if (weight.compareTo(10.0) > 0) {
            logger.info("Weight based parcel rules");
            return costRules.entrySet().stream().filter(entry -> entry.getKey().test(parcel)).map(Map.Entry::getValue).findFirst().orElseThrow(UnsupportedRuleException::new).getDeliveryCost(weight);
        }
        logger.info("volume based parcel rules");
        return costRules.entrySet().stream().filter(entry -> entry.getKey().test(parcel)).map(Map.Entry::getValue).findFirst().orElseThrow(UnsupportedRuleException::new).getDeliveryCost(volume);
    }
}
