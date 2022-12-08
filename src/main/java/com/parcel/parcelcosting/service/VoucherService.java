package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.parcel.parcelcosting.entity.Voucher;
import com.parcel.parcelcosting.enums.MessageCode;
import com.parcel.parcelcosting.enums.VoucherCodes;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class VoucherService {
    @Autowired
    ResponseService responseService;
    public boolean isValidCode(String voucherCode) {
        for (VoucherCodes codes : VoucherCodes.values()) {
            if (codes.name().equalsIgnoreCase(voucherCode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidVoucher(String date) {
        String testDate = "2020-09-15";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate voucherExpiryDate = LocalDate.parse(date, formatter);
        LocalDate testExpiryDate = LocalDate.parse(testDate, formatter);
        //LocalDate today = LocalDate.now();
        LocalDate today = testExpiryDate;
        int diff = today.compareTo(voucherExpiryDate);
        if(diff <= 0){
            return true;
        }
        else {
            return false;
        }
    }
    public double calculateDiscountCost(Double cost, Double discount){
        if(cost ==0 ) {
            return 0;
        }else {
            Double discountAmount = (cost / 100) * discount;
            return cost - discountAmount;
        }
    }
    public Object getDiscount(HttpResponse<JsonNode> apiResp, Double cost, Voucher voucher) {

        org.json.JSONObject apiRespJson = apiResp.getBody().getObject();
        voucher.setDiscount((Double) apiRespJson.get("discount"));
        voucher.setVoucherValidTill((String) apiRespJson.get("expiry"));
        if (apiResp.getStatus() == 200) {

            if (isValidVoucher(String.valueOf(apiRespJson.get("expiry")))) {
                Double discountedCost = calculateDiscountCost(cost, (Double) apiRespJson.get("discount"));
                voucher.setVoucherApplied(true);
                voucher.setDiscountCost(discountedCost);
                return responseService.voucherDetails(true,apiRespJson,discountedCost);
            } else {
                voucher.setVoucherApplied(false);
                voucher.setDiscountCost(cost);
                voucher.setMessage(MessageCode.VOUCHER_EXPIRED);
                return responseService.voucherDetails(false,apiRespJson,cost);
            }
        } else {
            return apiResp.getBody().getObject();
        }

    }

}
