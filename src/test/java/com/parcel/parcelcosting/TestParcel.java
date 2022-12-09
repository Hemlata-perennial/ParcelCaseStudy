package com.parcel.parcelcosting;

import com.parcel.parcelcosting.ParcelCostingApplicationTests;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.service.ParcelService;
import com.parcel.parcelcosting.service.ParcelServiceImpl;
import com.parcel.parcelcosting.service.VoucherServiceImpl;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TestParcel extends ParcelCostingApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Mock
    private ParcelService parcelService;

    @org.junit.jupiter.api.Test
    void calculateCostHeavyParcel() {
        Parcel parcel = new Parcel(20.376, 10.0, 10.0, 10.0);
        assertEquals(null, parcelService.getCost(parcel));
    }

    @org.junit.jupiter.api.Test
    void calculateCostSmallParcel() {
        Parcel parcel = new Parcel(5.33D, 10.33, 10.33D, 10.33D);
        assertEquals(BigDecimal.valueOf(33.07D), parcelService.getCost(parcel));
    }

    @org.junit.jupiter.api.Test
    void calculateCostMediumParcel() {
        Parcel parcel = new Parcel(5.33D, 5.33D, 50D, 8.67D);
        Assert.assertEquals(BigDecimal.valueOf(92.42D), parcelService.getCost(parcel));
    }

    @org.junit.jupiter.api.Test
    void calculateCostLargeParcel() {
        Parcel parcel = new Parcel(5.33D, 10.37D, 50.66D, 10D);
        assertEquals(BigDecimal.valueOf(262.67D), parcelService.getCost(parcel));
    }

    @org.junit.jupiter.api.Test
    void calculateCostWithDiscount() {
        Parcel parcel = new Parcel(10D, 10.37D, 10.33D, 10.66D);
        Assert.assertEquals(BigDecimal.valueOf(17.13D), parcelService.getCost(parcel));
    }
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

        Assert.assertEquals("{\"parcelCost\":0.74}", result.getBody().toString());
    }
}
