package com.vin.all.service;

import com.vin.all.persistence.entity.Bidders;
import com.vin.all.persistence.repository.BidderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vineetsinha on 8/19/18.
 */
@Service
public class BidderService {
    private static final Logger log = LoggerFactory.getLogger(BidderService.class);

    private final BidderRepository bidderRepository;

    public BidderService(BidderRepository bidderRepository) {
        this.bidderRepository = bidderRepository;
    }

    public Bidders verifyAndReturnUser (String bidderName, String userId) throws Exception {
        List<Bidders> bidders = this.bidderRepository.findByBidderName(bidderName);
        if (bidders==null) {
            throw new Exception("No user found");
        }
        else return bidders.stream()
                .filter(x -> x.getBidderEmail().equalsIgnoreCase(userId))
                .findFirst()
                .orElseThrow(() -> new Exception("User not matched with userId"));
    }

    public Bidders verifyAndAuthenticate (String bidderName, String userId) throws Exception {
        List<Bidders> bidders = this.bidderRepository.findByBidderName(bidderName);
        if (bidders==null) {
            return null;
        }
        else return bidders.stream()
                .filter(x -> x.getBidderEmail().equalsIgnoreCase(userId))
                .findFirst()
                .orElse(null);
    }
}
