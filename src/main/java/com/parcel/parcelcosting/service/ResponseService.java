package com.parcel.parcelcosting.service;

import com.parcel.parcelcosting.exception.ExceptionResponse;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
public class ResponseService {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    /**
     * Pepare json report containing
     * @param deliveryCost delivery cost calculated based on rule
     * @param discountedDeliveryCost  delivery cost calculated after applying voucher
     * **/
    public ResponseEntity<JSONObject> getDeliveryCostApiResponse(Double deliveryCost, Double discountedDeliveryCost){
        JSONObject response = new JSONObject();
        df.setRoundingMode(RoundingMode.UP);
        response.put("deliveryCost",df.format(deliveryCost));
        response.put("discountedDeliveryCost", df.format(discountedDeliveryCost));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public JSONObject exceptionMessage(Exception e){
        JSONObject response = new JSONObject();
        response.put("exception", new ExceptionResponse(e.getMessage()));
        return response;
    }
}
