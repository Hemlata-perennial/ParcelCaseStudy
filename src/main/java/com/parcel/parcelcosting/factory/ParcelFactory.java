package com.parcel.parcelcosting.factory;

import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.exception.RuleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;


/**
 * The purpose of the class is to define rules based volume and weight using design pattern
 */
public class ParcelFactory {
    static Logger logger = LoggerFactory.getLogger(ParcelFactory.class);
    private static final Map<Predicate<Parcel>, WeightRule> weightRules;
    private static final Map<Predicate<Parcel>, VolumeRule> volumeRules;

    static {
        weightRules = new LinkedHashMap<>();
        weightRules.put(parcel -> BigDecimal.valueOf(parcel.getWeight()).compareTo(BigDecimal.TEN) > 0, new HeavyParcelRule());

        volumeRules = new LinkedHashMap<>();
        volumeRules.put(parcel -> Parcel.getVolume(parcel).compareTo(BigDecimal.valueOf(1500)) < 0, new SmallParcelRule());
        volumeRules.put(parcel -> Parcel.getVolume(parcel).compareTo(BigDecimal.valueOf(2500)) < 0, new MediumParcelRule());
        volumeRules.put(parcel -> Parcel.getVolume(parcel).compareTo(BigDecimal.valueOf(2500)) >= 0, new LargeParcelRule());
    }

    private ParcelFactory() {
    }

    public static BigDecimal getDeliveryCost(final Parcel parcel) {
        BigDecimal parcelWeight;
        if (Optional.ofNullable(parcel.getWeight()).isPresent()) {
            parcelWeight = BigDecimal.valueOf(parcel.getWeight());
        } else {
            parcelWeight = BigDecimal.ZERO;
        }

        BigDecimal parcelVolume = Parcel.getVolume(parcel);

        //iterating through the rules defined in the map
        //and checking for the corresponding rule and type in which the parcel fits
        if (parcelWeight.compareTo(BigDecimal.TEN) > 0) {
            logger.info("Weight based parcel rules");
            return weightRules.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().test(parcel))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElseThrow(RuleNotFoundException::new)
                    .getDeliveryCost(parcelWeight);
        }
        logger.info("volume based parcel rules");
        return volumeRules.entrySet()
                .stream()
                .filter(entry -> entry.getKey().test(parcel))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(RuleNotFoundException::new)
                .getDeliveryCost(parcelVolume);
    }
}
