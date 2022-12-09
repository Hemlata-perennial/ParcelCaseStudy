package com.parcel.parcelcosting.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

@Component
public class HttpClient {
    public HttpResponse<JsonNode> makeHttpCall(String voucherCode, String url, String apiKey) throws UnirestException {
        HttpResponse<JsonNode> voucherAPIResponse = Unirest.get(url + "" + voucherCode + "?key=" + apiKey)
                .header("accept", "application/json")
                .asJson();
        return  voucherAPIResponse;
    }
}
