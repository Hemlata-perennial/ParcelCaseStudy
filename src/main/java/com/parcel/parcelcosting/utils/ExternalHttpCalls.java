package com.parcel.parcelcosting.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ExternalHttpCalls {
    public HttpResponse<JsonNode> makeHttpCall(String voucherCode, String url, String apiKey) throws UnirestException {
        HttpResponse<JsonNode> voucherAPIResponse = Unirest.get(url + "" + voucherCode + "?key=" + apiKey)
                .header("accept", "application/json")
                .asJson();
        return  voucherAPIResponse;
    }
}
