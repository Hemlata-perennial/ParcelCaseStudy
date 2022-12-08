package com.parcel.parcelcosting.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.entity.Voucher;
import com.parcel.parcelcosting.enums.MessageCode;
import com.parcel.parcelcosting.reporsiory.ParcelRepository;
import com.parcel.parcelcosting.reporsiory.VoucherRepository;
import com.parcel.parcelcosting.service.ParcelService;
import com.parcel.parcelcosting.service.ResponseService;
import com.parcel.parcelcosting.service.VoucherServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController("/parcel")
public class ParcelController {
    @Autowired
    ParcelRepository parcelRepository;

    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    ParcelService parcelService;

    @Autowired
    VoucherServiceImpl voucherService;

    @Autowired
    ResponseService responseService;

    @PostMapping("/delivery-cost")
    ResponseEntity<JSONObject> deliveryCost(@RequestBody Parcel parcel){
        try {
            JSONObject response = new JSONObject();
            parcel.setVolume(parcel.getHeight() * parcel.getWidth() * parcel.getLength());
            parcel.setCost(parcelService.getCost(parcel));
            parcelRepository.save(parcel);
            response.put("parcelStatus", parcel.getRule());
            response.put("parcelCost", parcel.getCost());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(responseService.exceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/delivery-cost/{voucherCode}")
    ResponseEntity<JSONObject> deliveryCostVoucher(@RequestBody Parcel parcel, @PathVariable String voucherCode) throws UnirestException {
        try {
            JSONObject response = new JSONObject();
            Voucher voucher = new Voucher();
            voucher.setCode(voucherCode);
            Double cost = (Double) voucherService.callDeliveryCostApi(parcel).get("parcelCost");
            response.put("parcelDetails", voucherService.callDeliveryCostApi(parcel));
            response.put("voucherDetails", voucherService.callVaucherApi(parcel, voucherCode, voucher, cost));
            voucherRepository.save(voucher);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(responseService.exceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
