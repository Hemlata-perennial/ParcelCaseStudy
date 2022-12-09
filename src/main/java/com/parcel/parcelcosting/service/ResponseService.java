package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.parcel.parcelcosting.enums.MessageCode;
import com.parcel.parcelcosting.exception.ExceptionResponse;
import com.parcel.parcelcosting.exception.InvalidVoucherException;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    public JSONObject voucherDetails(Double cost){
        JSONObject response = new JSONObject();
        response.put("discountedDeliveryCostCost",cost);
        return response;
    }

    public ResponseEntity<JSONObject> getDeliveryCostApiResponse(Double deliveryCost){
        JSONObject response = new JSONObject();
        response.put("deliveryCost", deliveryCost);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<JSONObject> getDeliveryCostVoucherApiResponse(JSONObject voucherApiResponse, Double deliveryCost){
        JSONObject response = new JSONObject();
        if(voucherApiResponse.get("discountedDeliveryCostCost") == null) {
            response.put("discountedDeliveryCostCost", deliveryCost);
            response.put("message", MessageCode.INVALID_VOUCHER_CODE);
            throw new InvalidVoucherException( MessageCode.INVALID_VOUCHER_CODE);

        }
        response.put("discountedDeliveryCostCost", voucherApiResponse.get("discountedDeliveryCostCost"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public JSONObject exceptionMessage(Exception e){
        JSONObject response = new JSONObject();
        response.put("exception", new ExceptionResponse(e.getMessage()));
        return response;
    }
}
