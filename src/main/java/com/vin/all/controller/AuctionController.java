package com.vin.all.controller;

import com.vin.all.model.AuctionVO;
import com.vin.all.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by vineetsinha on 8/18/18.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/v1")
public class AuctionController {
    private static final Logger log = LoggerFactory.getLogger(AuctionController.class);

    @Autowired
    private AuctionService auctionService;

    @PostMapping(value = "/auctionItems", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<AuctionVO> saveAuctionItems(@RequestBody AuctionVO auctionRequest,
                                                              @RequestHeader HttpHeaders headers)
            throws Exception
    {
        AuctionVO response = auctionService.saveAuctionItems(auctionRequest, headers);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/auctionItems", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<List<AuctionVO>> getAllAuctionItems(@RequestHeader HttpHeaders headers) {
        List<AuctionVO> response = auctionService.getAllAuctionItems(headers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/auctionItems/{auctionItemId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<AuctionVO> getAuctionItem(@PathVariable(value = "auctionItemId") String auctionItemId,
                                                          @RequestHeader HttpHeaders headers) {
        AuctionVO response = auctionService.getAuctionItem(auctionItemId, headers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
