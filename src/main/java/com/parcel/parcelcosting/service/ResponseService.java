package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.parcel.parcelcosting.enums.MessageCode;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    public JSONObject voucherDetails(Double cost){
        JSONObject response = new JSONObject();
        response.put("finalCost",cost);
        return response;
    }

    public JSONObject invalidVoucherCode(){
        JSONObject voucherDetails = new JSONObject();
        voucherDetails.put("message", MessageCode.INVALID_VOUCHER_CODE);
        voucherDetails.put("messageCode", "INVALID_VOUCHER_CODE");
        return voucherDetails;
    }

    public JSONObject parcelDetails(HttpResponse<JsonNode> costResp){
        JSONObject parcelDetails = new JSONObject();
        parcelDetails.put("parcelCost", costResp.getBody().getObject().get("parcelCost"));
        return parcelDetails;
    }

    public JSONObject exceptionMessage(Exception e){
        JSONObject response = new JSONObject();
        response.put("error", e.getMessage());
        response.put("errorCode", 500);
        return response;
    }
}
