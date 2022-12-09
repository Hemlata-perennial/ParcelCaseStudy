package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.exceptions.UnirestException;
public interface VoucherService {
    Double getDiscountedDeliveryCost(String voucherCode, Double cost) throws UnirestException;
}
