package com.vin.all.persistence.repository;

import com.vin.all.persistence.entity.AuctionItems;
import com.vin.all.persistence.entity.Items;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;


/**
 * Created by vineetsinha on 8/19/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AuctionItemsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AuctionItemsRepository auctionItemsRepository;


    @Test
    public void findByAuctionItemId() throws Exception {

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

        entityManager.persist(auctionItems);
        entityManager.flush();

        AuctionItems fetchItems = auctionItemsRepository.findByAuctionItemId("abcd-efgh-ijkl-mnop");
        Assertions.assertThat(fetchItems.getAuctionItemId()).isEqualTo(auctionItems.getAuctionItemId());
    }

}