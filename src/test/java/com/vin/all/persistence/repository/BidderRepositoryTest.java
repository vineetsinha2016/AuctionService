package com.vin.all.persistence.repository;

import com.vin.all.persistence.entity.Bidders;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;


/**
 * Created by vineetsinha on 8/19/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BidderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BidderRepository bidderRepository;
    @Test
    public void findByBidderName() throws Exception {
        Bidders bidder = new Bidders();
        bidder.setBidderEmail("abc@abc.com");
        bidder.setBidderName("First User");
        bidder.setRecSource("source");
        bidder.setAddByUser("me");
        bidder.setRecordAddTime(new Timestamp(new java.util.Date().getTime()));
        bidder.setUpdatedByUser("me");
        bidder.setRecordUpdatedTime(new Timestamp(new java.util.Date().getTime()));

        entityManager.persist(bidder);
        entityManager.flush();

        List<Bidders> fetchedBidders = bidderRepository.findByBidderName("First User");
        Bidders fetchedBidder = fetchedBidders.stream()
                .filter(x->x.getBidderEmail().equals(bidder.getBidderEmail()))
                .findFirst().orElse(null);
        Assertions.assertThat(fetchedBidder.getBidderName().equals(bidder.getBidderName()));
    }

}