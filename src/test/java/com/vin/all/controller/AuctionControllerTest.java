package com.vin.all.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vin.all.model.AuctionVO;
import com.vin.all.model.Item;
import com.vin.all.persistence.entity.AuctionItems;
import com.vin.all.persistence.entity.Items;
import com.vin.all.service.AuctionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vineetsinha on 8/19/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AuctionController.class)
public class AuctionControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuctionService auctionService;

    @Test
    public void saveAuctionItems() throws Exception {
        AuctionVO requestAuction = new AuctionVO("abcd-efgh-ijkl-mnop");
        requestAuction.setReservePrice(new Long(100));
        requestAuction.setCurrentBid(new Long(0));
        requestAuction.setItem(new Item("abcd","item Description"));
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(requestAuction);
        given(auctionService.saveAuctionItems(requestAuction, new HttpHeaders())).willReturn(requestAuction);
        mvc.perform(post("/v1/auctionItems").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    public void getAllAuctionItems() throws Exception {
        AuctionVO requestAuction = new AuctionVO("abcd-efgh-ijkl-mnop");
        requestAuction.setReservePrice(new Long(100));
        requestAuction.setCurrentBid(new Long(0));
        requestAuction.setItem(new Item("abcd","item Description"));
        given(auctionService.getAllAuctionItems(new HttpHeaders())).willReturn(Collections.singletonList(requestAuction));
        mvc.perform(get("/v1/auctionItems")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getAuctionItem() throws Exception {
        AuctionVO requestAuction = new AuctionVO("abcd-efgh-ijkl-mnop");
        requestAuction.setReservePrice(new Long(100));
        requestAuction.setCurrentBid(new Long(0));
        requestAuction.setItem(new Item("abcd","item Description"));
        given(auctionService.getAuctionItem("abcd-efgh-ijkl-mnop", new HttpHeaders())).willReturn(requestAuction);
        mvc.perform(get("/v1/auctionItems/abcd-efgh-ijkl-mnop")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

}