package com.parcel.parcelcosting.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.parcel.parcelcosting.controller.ParcelController;
import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.entity.Voucher;
import com.parcel.parcelcosting.enums.MessageCode;
import net.minidev.json.JSONObject;
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
    public JSONObject callDeliveryCostApi(Parcel parcel) throws UnirestException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("height", parcel.getHeight());
        requestBody.put("weight", parcel.getWeight());
        requestBody.put("length", parcel.getLength());
        requestBody.put("width", parcel.getWidth());
        HttpResponse<JsonNode> costResp = Unirest.post(costingUrl).header("Content-Type", "application/json").body(requestBody.toJSONString()).asJson();
        logger.info("delivery-cost api called with status: "+costResp.getStatusText());
        return responseService.parcelDetails(costResp);

    }
    @Override
    public Object callVaucherApi(Parcel parcel, String voucherCode, Voucher voucher, Double cost) throws UnirestException {
        HttpResponse<JsonNode> apiResp = Unirest.get(url + "" + voucherCode + "?key=" + apiKey).header("accept", "application/json").asJson();
        logger.info("voucher api called with status: "+apiResp.getStatusText());
        Object voucherDetails = getDiscount(apiResp, cost, voucher);
        return voucherDetails;
    }

    @Override
    public boolean isValidVoucher(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate voucherExpiryDate = LocalDate.parse(date, formatter);
        /* String testDate = "2020-09-15";
        LocalDate testExpiryDate = LocalDate.parse(testDate, formatter);
        LocalDate today = testExpiryDate; */
        LocalDate today = LocalDate.now();
        int diff = today.compareTo(voucherExpiryDate);
        if(diff <= 0){
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public double calculateDiscountCost(Double cost, Double discount){
        if(cost ==0 ) {
            return 0;
        }else {
            Double discountAmount = (cost / 100) * discount;
            return cost - discountAmount;
        }
    }
    @Override
    public Object getDiscount(HttpResponse<JsonNode> apiResp, Double cost, Voucher voucher) {

        org.json.JSONObject apiRespJson = apiResp.getBody().getObject();

        if (apiResp.getStatus() == 200) {
            voucher.setDiscount((Double) apiRespJson.get("discount"));
            voucher.setVoucherValidTill((String) apiRespJson.get("expiry"));
            if (isValidVoucher(String.valueOf(apiRespJson.get("expiry")))) {
                logger.info("Voucher is valid");
                Double discountedCost = calculateDiscountCost(cost, (Double) apiRespJson.get("discount"));
                voucher.setVoucherApplied(true);
                voucher.setDiscountCost(discountedCost);
                return responseService.voucherDetails(true,apiRespJson,discountedCost);
            } else {
                logger.info("Voucher is expired");
                voucher.setVoucherApplied(false);
                voucher.setDiscountCost(cost);
                voucher.setMessage(MessageCode.VOUCHER_EXPIRED);
                return responseService.voucherDetails(false,apiRespJson,cost);
            }
        } else if(apiResp.getStatus() == 400){
            logger.info("Voucher is invalid");
            voucher.setVoucherApplied(false);
            voucher.setDiscountCost(cost);
            voucher.setMessage(MessageCode.INVALID_VOUCHER_CODE);
            return responseService.invalidVoucherCode();
        }
        else {
            JSONObject voucherDetails = new JSONObject();
            return voucherDetails.put("message",apiRespJson);
        }

    }

}
