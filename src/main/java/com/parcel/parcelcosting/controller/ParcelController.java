package com.parcel.parcelcosting.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.entity.Voucher;
import com.parcel.parcelcosting.reporsiory.ParcelRepository;
import com.parcel.parcelcosting.reporsiory.VoucherRepository;
import com.parcel.parcelcosting.service.ParcelService;
import com.parcel.parcelcosting.service.ResponseService;
import com.parcel.parcelcosting.service.VoucherServiceImpl;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    Logger logger = LoggerFactory.getLogger(ParcelController.class);
    @PostMapping("/delivery-cost")
    ResponseEntity<JSONObject> deliveryCost(@RequestBody Parcel parcel){
        try {
            JSONObject response = new JSONObject();
            parcel.setVolume(parcel.getHeight() * parcel.getWidth() * parcel.getLength());
            parcel.setCost(parcelService.getCost(parcel));
            parcelRepository.save(parcel);
            logger.info("Parcel details has been saved successfully!");
            response.put("parcelStatus", parcel.getRule());
            response.put("parcelCost", parcel.getCost());
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
            Voucher voucher = new Voucher();
            voucher.setCode(voucherCode);
            JSONObject resp = voucherService.callDeliveryCostApi(parcel);
            Double cost = (Double) resp.get("parcelCost");
            response.put("parcelDetails", resp);
            response.put("voucherDetails", voucherService.callVaucherApi(parcel, voucherCode, voucher, cost));
            voucherRepository.save(voucher);
            logger.info("Voucher details has been saved successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(responseService.exceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
