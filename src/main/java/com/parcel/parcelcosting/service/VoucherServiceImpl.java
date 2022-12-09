package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.enums.MessageCode;
import com.parcel.parcelcosting.exception.InvalidVoucherException;
import com.parcel.parcelcosting.exception.VoucherExpiredException;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.SwitchPoint;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public JSONObject getDiscountedDeliveryCost(Parcel parcel, String voucherCode, Double cost) throws UnirestException {
        HttpResponse<JsonNode> apiResp = Unirest.get(url + "" + voucherCode + "?key=" + apiKey).header("accept", "application/json").asJson();
        logger.info("voucher api called with status: "+apiResp.getStatusText());
        return getDiscount(apiResp, cost);
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

    public JSONObject getDiscount(HttpResponse<JsonNode> apiResp, Double cost) {
        org.json.JSONObject apiRespJson = apiResp.getBody().getObject();
        logger.debug("Voucher API Response: "+apiRespJson.toString());
        if (apiResp.getStatus() == 200) {
            if (isValidVoucher(String.valueOf(apiRespJson.get("expiry")))) {
                logger.info("Voucher is valid");

                Double discountedCost = calculateDiscountCost(cost, (Double) apiRespJson.get("discount"));
                return responseService.voucherDetails(discountedCost);
            } else {

                logger.error(MessageCode.VOUCHER_EXPIRED);
                throw new VoucherExpiredException(MessageCode.VOUCHER_EXPIRED);
            }
        } else{

            logger.error(MessageCode.INVALID_VOUCHER_CODE);
            throw new InvalidVoucherException(MessageCode.INVALID_VOUCHER_CODE);
        }

    }

}
