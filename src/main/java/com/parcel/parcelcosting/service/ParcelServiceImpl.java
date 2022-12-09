package com.parcel.parcelcosting.service;

import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.factory.ParcelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class ParcelServiceImpl implements ParcelService {
    Logger logger = LoggerFactory.getLogger(ParcelService.class);
    @Override
    public BigDecimal getCost(Parcel parcel){
        return ParcelFactory.getDeliveryCost(parcel);

    }
}
