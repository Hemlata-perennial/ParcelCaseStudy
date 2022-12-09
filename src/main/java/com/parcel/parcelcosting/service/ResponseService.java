package com.parcel.parcelcosting.service;

import com.parcel.parcelcosting.enums.MessageCode;
import com.parcel.parcelcosting.exception.ExceptionResponse;
import com.parcel.parcelcosting.exception.InvalidVoucherException;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    public ResponseEntity<JSONObject> getDeliveryCostApiResponse(Double deliveryCost, Double discountedDeliveryCost){
        JSONObject response = new JSONObject();
        response.put("deliveryCost", deliveryCost);
        response.put("discountedDeliveryCost", discountedDeliveryCost);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public JSONObject exceptionMessage(Exception e){
        JSONObject response = new JSONObject();
        response.put("exception", new ExceptionResponse(e.getMessage()));
        return response;
    }
}
