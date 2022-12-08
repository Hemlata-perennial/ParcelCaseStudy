package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.parcel.parcelcosting.enums.MessageCode;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    public JSONObject voucherDetails(boolean valid,org.json.JSONObject apiRespJson,Double cost){
        JSONObject response = new JSONObject();
        response.put("voucherCode", apiRespJson.get("code"));
        response.put("discount", apiRespJson.get("discount"));
        response.put("voucherValidTill", apiRespJson.get("expiry"));
        response.put("voucherApplied", valid);
        response.put("discountCost",cost);
        if(!valid){
            response.put("message", MessageCode.VOUCHER_EXPIRED);
            response.put("messageCode", "VOUCHER_EXPIRED");
        }
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
        parcelDetails.put("parcelCost", costResp.getBody().getObject().get("cost"));
        parcelDetails.put("parcelType", costResp.getBody().getObject().get("status"));
        return parcelDetails;
    }

    public JSONObject exceptionMessage(Exception e){
        JSONObject response = new JSONObject();
        response.put("error", e.getMessage());
        response.put("errorCode", 500);
        return response;
    }
}
