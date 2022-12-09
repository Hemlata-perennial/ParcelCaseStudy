package com.parcel.parcelcosting;

import com.parcel.parcelcosting.ParcelCostingApplicationTests;

import java.net.URI;
import java.net.URISyntaxException;

import com.parcel.parcelcosting.entity.Parcel;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TestVoucher extends ParcelCostingApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void testVoucherResponseSuccess() throws URISyntaxException {
        final String baseUrl = "http://localhost:8082/delivery-cost/MYNT";
        URI uri = new URI(baseUrl);
        JSONObject parcel = new JSONObject();
        parcel.put("height",1.5);
        parcel.put("weight",10.0);
        parcel.put("length",6.1);
        parcel.put("width",20.7);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<JSONObject> request = new HttpEntity<>(parcel, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assert.assertEquals(200,result.getStatusCodeValue());
    }


    @Test
    public void testVoucherResponseFailure() throws URISyntaxException {
        final String baseUrl = "http://localhost:8082/delivery-cost/XYZ";
        URI uri = new URI(baseUrl);
        JSONObject parcel = new JSONObject();
        parcel.put("height",1.5);
        parcel.put("weight",10.0);
        parcel.put("length",6.1);
        parcel.put("width",2.7);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<JSONObject> request = new HttpEntity<>(parcel, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assert.assertEquals(200,result.getStatusCodeValue());
        Assert.assertEquals("{\"finalCost\":0.74,\"message\":\"Voucher code is not valid!!\",\"parcelCost\":0.74}", result.getBody().toString());

    }
    @Test
    public void testDiscountSuccess() throws URISyntaxException {
        final String baseUrl = "http://localhost:8082/delivery-cost/GFI";
        URI uri = new URI(baseUrl);
        JSONObject parcel = new JSONObject();
        parcel.put("height",1.5);
        parcel.put("weight",10.0);
        parcel.put("length",6.1);
        parcel.put("width",2.7);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<JSONObject> request = new HttpEntity<>(parcel, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assert.assertEquals("{\"finalCost\":0.74,\"parcelCost\":0.74}", result.getBody().toString());
    }
}
