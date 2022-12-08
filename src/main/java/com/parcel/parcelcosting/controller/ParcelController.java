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
import com.parcel.parcelcosting.service.VoucherService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController()
public class ParcelController {
    @Autowired
    ParcelRepository parcelRepository;

    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    ParcelService parcelService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    ResponseService responseService;

    @Value("${voucher.api.url}")
    private String url;
    @Value("${voucher.api.key}")
    private String apiKey;

    @Value("${cost.api.url}")
    private String costingUrl;


    @PostMapping("/cost")
    ResponseEntity<JSONObject> cost(@RequestBody Parcel parcel){
        try {
            JSONObject response = new JSONObject();
            parcel.setVolume(parcel.getHeight() * parcel.getWidth() * parcel.getLength());
            parcel.setCost(parcelService.getCost(parcel));
            parcelRepository.save(parcel);
            response.put("status", parcel.getRule());
            response.put("cost", parcel.getCost());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(responseService.exceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/cost/{voucherCode}")
    ResponseEntity<JSONObject> cost(@RequestBody Parcel parcel, @PathVariable String voucherCode) throws UnirestException {

        try {
            JSONObject response = new JSONObject();
            JSONObject requestBody = new JSONObject();

            Voucher voucher = new Voucher();
            voucher.setCode(voucherCode);

            //Call to cost API
            requestBody.put("height", parcel.getHeight());
            requestBody.put("weight", parcel.getWeight());
            requestBody.put("length", parcel.getLength());
            requestBody.put("width", parcel.getWidth());

            HttpResponse<JsonNode> costResp = Unirest.post(costingUrl).header("Content-Type", "application/json").body(requestBody.toJSONString()).asJson();
            response.put("parcelDeatils",responseService.parcelDetails(costResp));

            if (!voucherService.isValidCode(voucherCode)) {
                voucher.setMessage(MessageCode.INVALID_VOUCHER_CODE);
                voucherRepository.save(voucher);
                response.put("voucherDetails",responseService.invalidVoucherCode());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            }
            //Call to voucher API
            HttpResponse<JsonNode> apiResp = Unirest.get(url + "" + voucherCode + "?key=" + apiKey).header("accept", "application/json").asJson();
            Object voucherDetails = voucherService.getDiscount(apiResp, (Double) costResp.getBody().getObject().get("cost"), voucher);
            response.put("voucherDetails", voucherDetails);
            voucherRepository.save(voucher);
            return new ResponseEntity<>(response, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(responseService.exceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
