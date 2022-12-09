package com.parcel.parcelcosting.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.enums.MessageCode;
import com.parcel.parcelcosting.exception.ExceptionResponse;
import com.parcel.parcelcosting.exception.InvalidVoucherException;
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

    /**
     * @param parcel  Details of parcel such as height,weight,length,width
     * @param voucherCode The coupont code to apply discount on the calculated delivery cost
     * @return Delivery cost of the parcel based on the parcel details provided and the voucher applied.
     */
    @PostMapping("/delivery-cost/")
    ResponseEntity<JSONObject> deliveryCostVoucher(@RequestBody Parcel parcel, @RequestParam(required = false) final String voucherCode) throws UnirestException {
        try {
            logger.info("Processing voucher code "+voucherCode);
            return parcelService.getCost(parcel,voucherCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(responseService.exceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
