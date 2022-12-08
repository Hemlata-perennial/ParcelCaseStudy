package com.parcel.parcelcosting.service;

import com.parcel.parcelcosting.controller.ParcelController;
import com.parcel.parcelcosting.entity.Parcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ParcelServiceImpl implements ParcelService {
    Logger logger = LoggerFactory.getLogger(ParcelService.class);
    @Override
    public Double getCost(Parcel parcel){
        Double weight = parcel.getWeight();
        Double volume = parcel.getVolume();
        volume = volume * volume * volume;
        Double cost = 0.0;

        if (weight > 50) {
            logger.info("Parcel Rejected");
            parcel.setRule("Reject");
            cost = 0.0;
        } else if (weight > 10) {
            logger.info("Heavy Parcel having weight greater 10");
            parcel.setRule("Heavy Parcel");
            cost = 20 * weight;
        } else if (volume < 1500) {
            logger.info("small parcel volume less than 1500");
            parcel.setRule("Small Parcel");
            cost = 0.03 * volume;
        } else if (volume < 2500) {
            logger.info("medium Parcel having weight smaller 2500");
            parcel.setRule("Medium Parcel");
            cost = 0.04 * volume;
        } else if (volume > 2500) {
            logger.info("Large Parcel having weight greater 2500");
            parcel.setRule("Large Parcel");
            cost = 0.05 * volume;
        }
        return cost;
    }
}
