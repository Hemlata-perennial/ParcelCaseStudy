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
public class TestParcel extends ParcelCostingApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void testCalculateCostSuccess() throws URISyntaxException {
        final String baseUrl = "http://localhost:8082/delivery-cost";
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
    public void testCalculateCostResponseSuccess() throws URISyntaxException {
        final String baseUrl = "http://localhost:8082/delivery-cost";
        URI uri = new URI(baseUrl);
        JSONObject parcel = new JSONObject();
        parcel.put("height",1.5);
        parcel.put("weight",10.0);
        parcel.put("length",6.1);
        parcel.put("width",2.7);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<JSONObject> request = new HttpEntity<>(parcel, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assert.assertEquals("{\"parcelStatus\":\"Large Parcel\",\"parcelCost\":753.9188101312502}", result.getBody().toString());
    }
}
