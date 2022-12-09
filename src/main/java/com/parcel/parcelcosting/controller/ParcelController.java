package com.parcel.parcelcosting.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.enums.MessageCode;
import com.parcel.parcelcosting.service.ParcelService;
import com.parcel.parcelcosting.service.ResponseService;
import com.parcel.parcelcosting.service.VoucherServiceImpl;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController("/parcel")
public class ParcelController {
    @Autowired
    ParcelService parcelService;
    @Autowired
    VoucherServiceImpl voucherService;
    @Autowired
    ResponseService responseService;

    Logger logger = LoggerFactory.getLogger(ParcelController.class);

    @PostMapping("/delivery-cost")
    ResponseEntity<JSONObject> deliveryCost(@RequestBody Parcel parcel){
        try {
            JSONObject response = new JSONObject();
            BigDecimal DeliveryCost = parcelService.getCost(parcel);
            response.put("parcelCost", DeliveryCost);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(responseService.exceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/delivery-cost/{voucherCode}")
    ResponseEntity<JSONObject> deliveryCostVoucher(@RequestBody Parcel parcel, @PathVariable String voucherCode) throws UnirestException {
        try {
            JSONObject response = new JSONObject();
            response = voucherService.callDeliveryCostApi(parcel);
            Double cost = (Double) response.get("parcelCost");
            JSONObject voucherDetails = voucherService.callVaucherApi(parcel,voucherCode,cost);
            if(voucherDetails.get("finalCost") != null) {
                response.put("finalCost", voucherDetails.get("finalCost"));
            }
            else {
                response.put("finalCost", cost);
                response.put("message", MessageCode.INVALID_VOUCHER_CODE);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(responseService.exceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
