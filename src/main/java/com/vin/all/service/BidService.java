package com.vin.all.service;

import com.vin.all.model.BidRequestVO;
import com.vin.all.persistence.entity.AuctionItems;
import com.vin.all.persistence.entity.BidAuditLog;
import com.vin.all.persistence.entity.Bidders;
import com.vin.all.persistence.entity.Bids;
import com.vin.all.persistence.repository.AuctionItemsRepository;
import com.vin.all.persistence.repository.BidsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vineetsinha on 8/19/18.
 */
@Service
public class BidService {
    private static final Logger log = LoggerFactory.getLogger(BidService.class);

    private final BidsRepository bidsRepository;
    private final AuctionItemsRepository auctionItemsRepository;

    public BidService(BidsRepository bidsRepository, AuctionItemsRepository auctionItemsRepository) {
        this.bidsRepository = bidsRepository;
        this.auctionItemsRepository = auctionItemsRepository;
    }

    public BidRequestVO placeBid (final BidRequestVO bidRequestVO, HttpHeaders headers, Bidders bidder) throws Exception {

        AuctionItems auctionItem = auctionItemsRepository.findByAuctionItemId(
                bidRequestVO.getAuctionItemId());
        if (auctionItem.getReservePrice() > bidRequestVO.getMaxAutoBidAmount()
                /*&& auctionItem.getRpMetIndicator().equals("N")*/) {
            auctionItem.setCurrentBid(Math.max(auctionItem.getCurrentBid(), bidRequestVO.getMaxAutoBidAmount()));
            //auctionItemsRepository.saveAndFlush(auctionItem);
            //save bidding log
            saveBiddingLog(bidRequestVO, headers, bidder, auctionItem);
            log.info("Entity saved");
            //throw new Exception("Reserve Price has not been met");
            BidRequestVO bidVO = new BidRequestVO();
            bidVO.setResult("Reserve Price has not been met");
            return bidVO;
        }
        else if (auctionItem.getReservePrice() < bidRequestVO.getMaxAutoBidAmount()
                /*&& auctionItem.getRpMetIndicator().equals("N")*/){
            //reserve price met
            log.info("**************************************************"+auctionItem);
            List<Bids> bidsList = bidsRepository.findByAuctionItemAucId(auctionItem.getAucId());//findAll();//OrderByMaxAutoBidAmountDesc();
            log.info("bidsList fetched -->"+bidsList);
            List<Bids> sortedList = bidsList.stream()
                    .sorted(Comparator.comparingLong(
                            Bids::getMaxAutoBidAmount).reversed())
                    .collect(Collectors.toList());
            log.info("bidsListbyDescending order-->"+sortedList);

            if (sortedList.size()==0 || (sortedList.size()>0 && sortedList.get(0)!=null
                    && sortedList.get(0).getMaxAutoBidAmount() < bidRequestVO.getMaxAutoBidAmount())) {
                //save as current bid
                auctionItem.setCurrentBid(bidRequestVO.getMaxAutoBidAmount());
                auctionItem.setUpdatedByUser(headers.getFirst("userId"));
                auctionItem.setRecordUpdatedTime(new Timestamp(new Date().getTime()));
                //auctionItemsRepository.saveAndFlush(auctionItem);
            }
            List<Bids> outCastedBidList = bidsList.stream()
                    .filter(x->x.getMaxAutoBidAmount() < bidRequestVO.getMaxAutoBidAmount())
                    .collect(Collectors.toList());
            outCastedBidList.forEach(x->log.info("send email to each of these"+x.getBidder().getBidderEmail()));
            //place the bet
            saveBiddingLog(bidRequestVO, headers, bidder, auctionItem);
            BidRequestVO bidVO = new BidRequestVO();
            bidVO.setResult("bet placed");
            return bidVO;


        }
        return bidRequestVO;
    }

    private void saveBiddingLog(BidRequestVO bidRequestVO, HttpHeaders headers, Bidders bidder, AuctionItems auctionItem) {
        Bids bid = new Bids();
        bid.setAuctionItem(auctionItem);
        bid.setBidder(bidder);
        bid.setMaxAutoBidAmount(bidRequestVO.getMaxAutoBidAmount());
        bid.setOutbidInd("N");
        bid.setRecSource(headers.getFirst("rec-source"));
        bid.setRecordAddTime(new Timestamp(new Date().getTime()));
        bid.setAddByUser(headers.getFirst("userId"));
        bid.setUpdatedByUser(headers.getFirst("userId"));
        bid.setRecordUpdatedTime(new Timestamp(new Date().getTime()));
        BidAuditLog bidAuditLog = new BidAuditLog();
        bidAuditLog.setBid(bid);
        bidAuditLog.setRecSource(headers.getFirst("rec-source"));
        bidAuditLog.setRecordAddTime(new Timestamp(new Date().getTime()));
        bidAuditLog.setAddByUser(headers.getFirst("userId"));
        bidAuditLog.setUpdatedByUser(headers.getFirst("userId"));
        bid.addAuditLog(bidAuditLog);
        bid = bidsRepository.save(bid);
    }

}
