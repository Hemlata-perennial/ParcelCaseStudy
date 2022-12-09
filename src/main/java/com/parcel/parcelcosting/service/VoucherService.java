package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import net.minidev.json.JSONObject;

public interface VoucherService {
    JSONObject getDiscountedDeliveryCost(Parcel parcel, String voucherCode, Double cost) throws UnirestException;

}
