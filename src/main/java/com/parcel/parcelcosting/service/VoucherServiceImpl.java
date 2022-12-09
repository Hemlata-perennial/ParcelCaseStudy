package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.enums.MessageCode;
import com.parcel.parcelcosting.exception.InvalidVoucherException;
import com.parcel.parcelcosting.exception.VoucherExpiredException;
import com.sun.org.apache.bcel.internal.generic.SWITCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService{
    Logger logger = LoggerFactory.getLogger(VoucherService.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ResponseService responseService;

    @Value("${cost.api.url}")
    private String costingUrl;

    @Value("${voucher.api.url}")
    private String url;
    @Value("${voucher.api.key}")
    private String apiKey;

    @Override
    public Double getDiscountedDeliveryCost(String voucherCode, Double cost) throws UnirestException {
        return calculateDiscountCost(cost,getDiscount(voucherCode));
    }

    public boolean isValidVoucher(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate voucherExpiryDate = LocalDate.parse(date, formatter);
        LocalDate today = LocalDate.now();
        int dateDifference = today.compareTo(voucherExpiryDate);
        return (dateDifference > 0 )  ? false: true;
    }
    public double calculateDiscountCost(Double cost, Double discount){
        if(cost > 0 ) {
            Double discountAmount = (cost / 100) * discount;
            logger.info("Discount applied");
            return cost - discountAmount;
        }
        logger.info("No discount applied");
        return cost;
    }

    public HttpResponse<JsonNode> makeHttpCall(String voucherCode) throws UnirestException {
         HttpResponse<JsonNode> voucherAPIResponse = Unirest.get(url + "" + voucherCode + "?key=" + apiKey)
                .header("accept", "application/json")
                .asJson();
         return  voucherAPIResponse;

    }
    public Double getDiscount(String voucherCode) throws UnirestException {
        logger.info("Processing voucher code");
        if (voucherCode == null || voucherCode == "") {
            logger.info("No voucher to apply");
            return 0.0;
        }
        HttpResponse<JsonNode> voucherAPIResponse=makeHttpCall(voucherCode);
        if(voucherAPIResponse.getStatus() == 200 && isValidVoucher((String) voucherAPIResponse.getBody().getObject().get("expiry"))){
            logger.error("Calculated discount");
            return (Double) voucherAPIResponse.getBody().getObject().get("discount");
        }
        if(voucherAPIResponse.getStatus() == 400){
            logger.error(MessageCode.INVALID_VOUCHER_CODE);
            throw new InvalidVoucherException(MessageCode.INVALID_VOUCHER_CODE);
        }
        else {
            logger.error(MessageCode.VOUCHER_EXPIRED);
            throw new VoucherExpiredException(MessageCode.VOUCHER_EXPIRED);
        }
    }

}


