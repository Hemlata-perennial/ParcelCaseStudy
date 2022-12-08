package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.entity.Voucher;
import net.minidev.json.JSONObject;

public interface VoucherService {

    JSONObject callDeliveryCostApi(Parcel parcel) throws UnirestException;
    public Object callVaucherApi(Parcel parcel, String voucherCode, Voucher voucher, Double cost) throws UnirestException;
    boolean isValidVoucher(String date);
    double calculateDiscountCost(Double cost, Double discount);
    Object getDiscount(HttpResponse<JsonNode> apiResp, Double cost, Voucher voucher);
}
