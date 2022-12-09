package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.factory.ParcelFactory;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class ParcelServiceImpl implements ParcelService {
    @Autowired
    VoucherService voucherService;

    @Autowired
    ResponseService responseService;
    Logger logger = LoggerFactory.getLogger(ParcelService.class);
    @Override
    public ResponseEntity<JSONObject> getCost(Parcel parcel, String voucherCode) throws UnirestException {
        Double deliveryCost = ParcelFactory.getDeliveryCost(parcel);
        logger.info("calculated delivery cost:" + deliveryCost);
        Double discountedDeliveryCost = voucherService.getDiscountedDeliveryCost(voucherCode,deliveryCost);
        logger.info("calculated discounted delivery cost:" + discountedDeliveryCost);

        return responseService.getDeliveryCostApiResponse(deliveryCost,discountedDeliveryCost);
    }
}
