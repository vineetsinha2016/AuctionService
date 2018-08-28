package com.vin.all.controller;

import com.vin.all.AuctionServiceApplication;
import com.vin.all.model.AuctionVO;
import com.vin.all.model.Item;
import com.vin.all.persistence.entity.AuctionItems;
import com.vin.all.persistence.entity.Items;
import com.vin.all.service.AuctionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created by vineetsinha on 8/20/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuctionServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AuctionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void saveAuctionItems() throws Exception {

        AuctionItems auctionItems = new AuctionItems();
        auctionItems.setCurrentBid(new Long(0));
        auctionItems.setRpMetIndicator("N");
        auctionItems.setReservePrice(Long.valueOf(10111));
        auctionItems.setAuctionItemId("abcd-efgh-ijkl-mnop");
        Items items = new Items();
        items.setItemId("abcd");
        items.setItemDescription("item Description");
        items.setRecSource("source");
        items.setAddByUser("me");
        items.setRecordAddTime(new Timestamp(new java.util.Date().getTime()));
        items.setUpdatedByUser("me");
        items.setRecordUpdatedTime(new Timestamp(new java.util.Date().getTime()));
        items.setAuctionItems(auctionItems);
        auctionItems.setItems(items);
        auctionItems.setRecSource("source");
        auctionItems.setAddByUser("me");
        auctionItems.setRecordAddTime(new Timestamp(new java.util.Date().getTime()));
        auctionItems.setUpdatedByUser("me");
        auctionItems.setRecordUpdatedTime(new Timestamp(new java.util.Date().getTime()));
        AuctionVO requestAuction = new AuctionVO("abcd-efgh-ijkl-mnop");
        requestAuction.setReservePrice(new Long(100));
        //requestAuction.setCurrentBid(new Long(0));
        requestAuction.setItem(new Item(items.getItemId(),items.getItemDescription()));
        headers.set("userId","firstUser@ally.com");
        headers.set("rec-source","postman");
        headers.set("user-info","MyInfo");
        HttpEntity<AuctionVO> entity = new HttpEntity<>(requestAuction, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/auction/v1/auctionItems"),
                HttpMethod.POST, entity, String.class);
        System.out.println("response-->"+response);
        String actual = response.getStatusCode().toString();
        assertTrue(response.getStatusCodeValue()==201);
    }

    @Test
    public void getAllAuctionItems() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/auction/v1/auctionItems"),
                HttpMethod.GET, entity, String.class);

        String expected = "[]";
        System.out.println("response-->"+response);
        assertTrue(response.getStatusCodeValue()==200);
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void getAuctionItem() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/auction/v1/auctionItems/abcd-efgh-ijkl-mnop"),
                HttpMethod.GET, entity, String.class);
        System.out.println("response-->"+response);
        String expected = "{}";
        assertTrue(response.getStatusCodeValue()==200);
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}