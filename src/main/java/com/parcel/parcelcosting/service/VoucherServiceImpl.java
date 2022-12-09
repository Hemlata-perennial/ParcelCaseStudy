package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.parcel.parcelcosting.enums.MessageCode;

import com.parcel.parcelcosting.utils.ExternalHttpCalls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class VoucherServiceImpl implements VoucherService{
    @Value("${voucher.api.url}")
    public String url;
    @Value("${voucher.api.key}")
    public String apiKey;
    Logger logger = LoggerFactory.getLogger(VoucherService.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ResponseService responseService;
    ExternalHttpCalls httpCalls = new ExternalHttpCalls();

    @Override
    public Double getDiscountedDeliveryCost(String voucherCode, Double cost) throws UnirestException {
        return calculateDiscountCost(cost,getDiscount(voucherCode));
    }

    public boolean isValidVoucher(String date) {
        //TODO: comment logger
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate voucherExpiryDate = LocalDate.parse(date, formatter);
        LocalDate today = LocalDate.now();
        int dateDifference = today.compareTo(voucherExpiryDate);
        return (dateDifference > 0 )  ? false: true;
    }
    public double calculateDiscountCost(Double cost, Double discount){
        if(cost > 0 && discount > 0.0) {
            Double discountAmount = (cost / 100) * discount;
            logger.info("Discount applied: "+discount+" on cost:"+cost);
            return cost - discountAmount;
        }
        logger.info("No discount applied");
        return cost;
    }


    public Double getDiscount(String voucherCode) throws UnirestException {
        logger.info("Processing voucher code");
        if (voucherCode == null || voucherCode == "") {
            logger.info("No voucher to apply");
            return 0.0;
        }
        HttpResponse<JsonNode> voucherAPIResponse=httpCalls.makeHttpCall(voucherCode,url,apiKey);
        //if(voucherAPIResponse.getStatus() == 200 && isValidVoucher((String) voucherAPIResponse.getBody().getObject().get("expiry"))){
        if(voucherAPIResponse.getStatus() == 200){
            logger.info("Calculing discount");
            return (Double) voucherAPIResponse.getBody().getObject().get("discount");
        }
        else {
            logger.error(voucherAPIResponse.getBody().toString());
            return 0.0;
        }
    }

}


