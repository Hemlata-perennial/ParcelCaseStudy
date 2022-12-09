package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;

public interface VoucherService {
    Double getDiscountedDeliveryCost(String voucherCode, Double cost) throws UnirestException;
}
