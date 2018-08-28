package com.vin.all.service;

import com.vin.all.model.AuctionVO;
import com.vin.all.model.Item;
import com.vin.all.persistence.entity.AuctionItems;
import com.vin.all.persistence.entity.Items;
import com.vin.all.persistence.repository.AuctionItemsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by vineetsinha on 8/19/18.
 */
@RunWith(SpringRunner.class)
public class AuctionServiceTest {


    private AuctionService auctionService;
    @MockBean
    private AuctionItemsRepository auctionItemsRepository;
    @Before
    public void setUp()throws Exception {
        auctionService = new AuctionService(auctionItemsRepository);

        AuctionItems auctionItems = new AuctionItems();
        auctionItems.setCurrentBid(new Long(0));
        auctionItems.setRpMetIndicator("N");
        auctionItems.setReservePrice(Long.valueOf(10111));
        auctionItems.setAuctionItemId("abcd-efgh-ijkl-mnop");
        Items items = new Items();
        items.setItemId("abcd");
        items.setItemDescription("item Description");
        items.setAuctionItems(auctionItems);
        auctionItems.setItems(items);
        List<AuctionItems> auctionItemsList = Collections.singletonList(auctionItems);
        Mockito.when(auctionItemsRepository
                .findByAuctionItemId(auctionItems
                        .getAuctionItemId()))
                .thenReturn(auctionItems);
        Mockito.when(auctionItemsRepository
                .save(auctionItems))
                .thenReturn(auctionItems);
        Mockito.when(auctionItemsRepository
                .findAll())
                .thenReturn(auctionItemsList);
    }
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
        requestAuction.setCurrentBid(new Long(0));
        requestAuction.setItem(new Item(items.getItemId(),items.getItemDescription()));
        AuctionVO result = auctionService.saveAuctionItems(requestAuction, new HttpHeaders());
        System.out.println("result-->"+result);
        assertThat(result.getAuctionItemId()).isNotNull();

    }

    @Test
    public void getAllAuctionItems() throws Exception {
        List<AuctionVO> resultList = auctionService.getAllAuctionItems(new HttpHeaders());
        AuctionVO result = resultList.stream().filter(x->x.getAuctionItemId().equals("abcd-efgh-ijkl-mnop")).findFirst().orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getAuctionItemId()).isEqualTo("abcd-efgh-ijkl-mnop");
    }

    @Test
    public void getAuctionItem() throws Exception {
        AuctionVO result = auctionService.getAuctionItem("abcd-efgh-ijkl-mnop", new HttpHeaders());
        assertThat(result).isNotNull();
        assertThat(result.getAuctionItemId()).isEqualTo("abcd-efgh-ijkl-mnop");
    }

}