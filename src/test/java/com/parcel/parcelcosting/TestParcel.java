package com.parcel.parcelcosting;

import com.parcel.parcelcosting.ParcelCostingApplicationTests;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.factory.ParcelFactory;
import com.parcel.parcelcosting.factory.ParcelRules;
import com.parcel.parcelcosting.service.ParcelService;
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




@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TestParcel extends ParcelCostingApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Mock
    private ParcelService parcelService;

    @Mock
    private ParcelRules.RejectParcel rejectParcel;

    @Mock
    private ParcelRules.HeavyParcel heavyParcel;

    @Mock
    private ParcelRules.LargeParcel largeParcel;

    @Mock
    private ParcelRules.MediumParcel mediumParcel;

    @Mock
    private ParcelRules.SmallParcel smallParcel;


    @Test
    public void testRejectParcel(){
        Assert.assertEquals(Double.valueOf(0.0),rejectParcel.getDeliveryCost(70.0));
    }
    @Test
    public void testHeavyParcel(){
        Assert.assertEquals(Double.valueOf(0.0),heavyParcel.getDeliveryCost(30.0));
    }

    @Test
    public void testLargeParcel(){
        Assert.assertEquals(Double.valueOf(0.0),largeParcel.getDeliveryCost(3000.0));
    }

    @Test
    public void testMeadiumParcel(){
        Assert.assertEquals(Double.valueOf(0.0),mediumParcel.getDeliveryCost(1700.0));
    }

    @Test
    public void testSmallParcel(){
        Assert.assertEquals(Double.valueOf(0.0),smallParcel.getDeliveryCost(1000.0));
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

        Assert.assertEquals("{\"parcelCost\":0.74115}", result.getBody().toString());
    }
}
