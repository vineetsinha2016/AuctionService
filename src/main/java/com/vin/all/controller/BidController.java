package com.vin.all.controller;

import com.vin.all.model.AuctionVO;
import com.vin.all.model.BidRequestVO;
import com.vin.all.model.UserCredentials;
import com.vin.all.persistence.entity.Bidders;
import com.vin.all.service.BidService;
import com.vin.all.service.BidderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vineetsinha on 8/19/18.
 */
@RestController
@CrossOrigin("*")
@RequestMapping(value="/v1")
public class BidController {
    private static final Logger log = LoggerFactory.getLogger(BidController.class);

    @Autowired
    private BidderService bidderService;
    @Autowired
    private BidService bidService;


    @PostMapping(value = "/bids", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> saveBids(@RequestBody BidRequestVO bidRequest,
                                                      @RequestHeader HttpHeaders headers)
            throws Exception
    {
        if(headers.getFirst("userId")==null || bidRequest.getBidderName()==null) {
            throw new Exception("UserId and Bidder name cannot be null");
        }
        log.info("bidderName-->"+bidRequest.getBidderName() + " headers.getFirst(\"userId\")"+headers.getFirst("userId"));
        Bidders bidder = bidderService.verifyAndReturnUser(bidRequest.getBidderName(), headers.getFirst("userId"));
        BidRequestVO response = bidService.placeBid(bidRequest, headers, bidder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/users/authenticate", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> verifyAndReturnUser(@RequestBody UserCredentials userCredentials,
                                      @RequestHeader HttpHeaders headers)
            throws Exception
    {
        log.info("FirstName-->"+userCredentials.getFirstName() + " emailId"+userCredentials.getUserName());
        Bidders bidder = bidderService.verifyAndAuthenticate(userCredentials.getFirstName(), userCredentials.getUserName());
        UserCredentials user = new UserCredentials();
        if (bidder!=null) {
            user.setFirstName(bidder.getBidderName());
            user.setUserName(bidder.getBidderEmail());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }
}
