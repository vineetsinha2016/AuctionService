package com.vin.all.service;

import com.vin.all.model.BidRequestVO;
import com.vin.all.persistence.entity.AuctionItems;
import com.vin.all.persistence.entity.Bidders;
import com.vin.all.persistence.entity.Bids;
import com.vin.all.persistence.entity.Items;
import com.vin.all.persistence.repository.AuctionItemsRepository;
import com.vin.all.persistence.repository.BidsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by vineetsinha on 8/19/18.
 */
@RunWith(SpringRunner.class)
public class BidServiceTest {
    private BidService bidService;
    @MockBean
    private AuctionItemsRepository auctionItemsRepository;
    @MockBean
    private BidsRepository bidsRepository;

    @Before
    public void setUp()throws Exception {
        bidService = new BidService(bidsRepository, auctionItemsRepository);

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
        Mockito.when(auctionItemsRepository
                .findByAuctionItemId(auctionItems
                        .getAuctionItemId()))
                .thenReturn(auctionItems);
        Bids bid = new Bids();
        bid.setMaxAutoBidAmount(Long.valueOf(10555));
        bid.setAuctionItem(auctionItems);
        Bidders bidder = new Bidders();
        bidder.setBidderName("FirstUser");
        bidder.setBidderEmail("firstuser@ally.com");
        bid.setBidder(bidder);

    }
    @Test
    public void placeBid() throws Exception {
        BidRequestVO bidRequestVO = new BidRequestVO();
        bidRequestVO.setBidderName("FirstUser");
        bidRequestVO.setAuctionItemId("abcd-efgh-ijkl-mnop");
        bidRequestVO.setMaxAutoBidAmount(Long.valueOf(10555));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("userId","firstuser@ally.com");
        Bidders bidder = new Bidders();
        bidder.setBidderName("FirstUser");
        bidder.setBidderEmail("firstuser@ally.com");
        BidRequestVO result = bidService.placeBid(bidRequestVO, httpHeaders, bidder);
        assertThat(result.getResult()).isNotNull();
        assertThat(result.getResult()).isEqualTo("bet placed");
    }

}
