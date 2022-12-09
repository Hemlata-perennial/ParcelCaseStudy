package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;

public interface ParcelService {
    public ResponseEntity<JSONObject> getCost(Parcel parcel, String voucherCode) throws UnirestException;
}
